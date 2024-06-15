package composite;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.*;
import java.util.stream.*;

import shapes.GraphicObject;
import view.GraphicObjectPanel;
import java.awt.geom.Point2D;

public class GraphicObjectManager {
    private GraphicObjectPanel gpanel;
    private Map<Integer, GraphicObject> objects = new HashMap<>();
    private Map<Integer, GraphicObject> objectsRemoved = new HashMap<>();
    private Map<Integer, GroupObject> groups = new HashMap<>();

    private AtomicInteger ObjectnextId = new AtomicInteger(1); // Id che inizia da 1 per tutti gli oggetti
    private int GroupNextId = 1000;

    public GraphicObjectManager(GraphicObjectPanel gpanel) {
        this.gpanel = gpanel;
    }

    public int addGraphicObject(GraphicObject obj) {
        int id = ObjectnextId.getAndIncrement();
        obj.setId(id);
        objects.put(id, obj);
        System.out.println("Added object with ID " + id + ": " + obj);
        return id;
    }

    public boolean removeGraphicObject(int id) {
        if (objects.containsKey(id)) {
            GraphicObject obj = objects.get(id);
            if (id >= 1000 && obj instanceof GroupObject) {
                GroupObject group = (GroupObject) obj;
                objectsRemoved.put(id, obj);
                for (GraphicObject member : group.getMembers()) {
                    int memberId = member.getId();
                    gpanel.remove(member);
                    objects.remove(memberId);
                    System.out.println("Removed member with ID " + memberId);
                }

                gpanel.remove(group);
                gpanel.repaint();
                System.out.println("Removed group with ID " + id);
            } else if (id < 1000) {
                gpanel.remove(obj);
                objectsRemoved.put(id, obj);
                objects.remove(id);
                gpanel.repaint();
                System.out.println("Removed object with ID " + id);
            }

            // Decrement ObjectNextId if the removed ID was the latest one assigned
            synchronized (ObjectnextId) {
                if (id == ObjectnextId.get() - 1) {
                    ObjectnextId.decrementAndGet();
                }
            }

            return true;
        }
        System.out.println("No object found with ID " + id);
        return false;
    }

    public boolean recoverElement(int id) {
        if (objectsRemoved.containsKey(id)) {
            GraphicObject obj = objectsRemoved.get(id);

            if (id >= 1000 && obj instanceof GroupObject) {
                GroupObject group = (GroupObject) obj;

                for (GraphicObject member : group.getMembers()) {
                    gpanel.add(member);
                    objects.put(member.getId(), member);

                }

                gpanel.repaint();
                System.out.println("Removed group with ID " + id);
            } else if (id < 1000) {
                objects.put(obj.getId(), obj);
                gpanel.add(obj);
                gpanel.repaint();
                System.out.println("Removed object with ID " + id);
            }
            return true;
        }
        System.out.println("No object found with ID " + id);
        return false;
    }

    public int createGroup(List<Integer> memberIds) {
        GroupObject group = new GroupObject(GroupNextId++);
        groups.put(group.getId(), group);
        for (int id : memberIds) {
            if (objects.containsKey(id)) {
                group.addMember(objects.get(id));
            } else {
                System.out.println("Member with ID " + id + " not found");
            }
        }

        objects.put(group.getId(), group);

        System.out.println("Group created with ID " + group.getId() + ": " + group);
        return group.getId();
    }

    public boolean removeGroup(int groupId) {
        if (objects.containsKey(groupId) && objects.get(groupId) instanceof GroupObject) {
            objectsRemoved.put(groupId, objects.get(groupId));
            objects.remove(groupId);
            GroupNextId--;
            System.out.println("Group with ID " + groupId + " removed.");
            return true;
        }
        System.out.println("No group found with ID " + groupId);
        return false;
    }

    public boolean recoverGroup(int groupId) {
        if (objectsRemoved.containsKey(groupId)) {
            GraphicObject obj = objectsRemoved.get(groupId);
            objects.put(obj.getId(), obj);
            System.out.println("Group with ID " + groupId + " recovered.");
            return true;
        }
        return false;
    }

    public void printGroupDetails(int groupId) {
        GraphicObject group = objects.get(groupId);
        if (group instanceof GroupObject) {
            System.out.println(group);
        } else {
            System.out.println("No group found with ID " + groupId);
        }
    }

    public boolean moveObject(int objectId, Point2D newPosition) {
        GraphicObject object = objects.get(objectId);
        if (object != null) {
            object.moveTo(newPosition);
            System.out.println("Moved object with ID " + objectId + " to new position: " + newPosition);
            return true;
        } else {
            System.out.println("No object found with ID " + objectId);
            return false;
        }
    }

    public boolean moveObjectOffset(int objectId, double offsetX, double offsetY) {
        GraphicObject object = getObject(objectId);
        if (object != null) {
            if (objectId >= 1000 && object instanceof GroupObject) {
                GroupObject group = (GroupObject) object;
                for (GraphicObject member : group.getMembers()) {
                    Point2D memberPosition = member.getPosition();
                    Point2D newMemberPosition = new Point2D.Double(memberPosition.getX() + offsetX,
                            memberPosition.getY() + offsetY);
                    member.moveTo(newMemberPosition);
                    System.out.println("Member ID " + member.getId() + " moved from " + memberPosition + " to "
                            + newMemberPosition);
                }
                System.out.println("Moved group with ID " + objectId);
            } else {
                Point2D currentPosition = object.getPosition();
                Point2D newPosition = new Point2D.Double(currentPosition.getX() + offsetX,
                        currentPosition.getY() + offsetY);
                object.moveTo(newPosition);
                System.out.println("Moved object with ID " + objectId + " to " + newPosition);
            }
            return true;
        } else {
            System.out.println("No object found with ID " + objectId);
            return false;
        }
    }

    public Map<Integer, Point2D> getObjectPositions(int objectId) {
        Map<Integer, Point2D> positions = new HashMap<>();
        GraphicObject object = objects.get(objectId);
        if (object != null) {
            if (object instanceof GroupObject) {
                GroupObject group = (GroupObject) object;
                for (GraphicObject member : group.getMembers()) {
                    positions.put(member.getId(), member.getPosition());
                }
            } else {
                positions.put(objectId, object.getPosition());
            }
        }
        return positions;
    }

    public GraphicObject getObject(int objectId) {
        return objects.get(objectId);
    }

    public String listAllObjects() {
        return objects.entrySet()
                .stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue().toString())
                .collect(Collectors.joining("\n"));
    }

    public String listAllGroups() {
        return objects.entrySet()
                .stream()
                .filter(entry -> entry.getValue() instanceof GroupObject)
                .map(entry -> "Group ID " + entry.getKey() + ": " + entry.getValue().toString())
                .collect(Collectors.joining("\n"));
    }

    public String listObjectDetails(int objectId) {
        GraphicObject obj = objects.get(objectId);
        if (obj != null) {
            return obj.toString();
        }
        return "No object found with ID " + objectId;
    }

    public String listObjectsByType(String type) {
        Set<GraphicObject> uniqueObjects = new HashSet<>(); // Uso un set per evitare duplicati

        objects.values().forEach(obj -> {
            if (obj instanceof GroupObject) {
                // Aggiungo tutti i membri del gruppo al set
                uniqueObjects.addAll(((GroupObject) obj).getMembers());
            } else {
                // Aggiungo il singolo ooggetto al set
                uniqueObjects.add(obj);
            }
        });

        return uniqueObjects.stream()
                .filter(obj -> obj.getType().equalsIgnoreCase(type))
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }

    public List<GraphicObject> listObjectsByTypePerimeter(String type) {
        Set<GraphicObject> uniqueObjects = new HashSet<>();

        objects.values().forEach(obj -> {
            if (obj instanceof GroupObject) {
                uniqueObjects.addAll(((GroupObject) obj).getMembers());
            } else {
                uniqueObjects.add(obj);
            }
        });

        return uniqueObjects.stream()
                .filter(obj -> obj.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    public double calculateArea(int objectId) {
        GraphicObject object = objects.get(objectId);
        if (object == null) {
            System.out.println("No object found with ID: " + objectId);
            return 0;
        }

        if (object instanceof GroupObject) {
            double totalArea = 0;
            GroupObject group = (GroupObject) object;
            for (GraphicObject member : group.getMembers()) {
                totalArea += member.getArea();
            }
            return totalArea;
        } else {
            return object.getArea();
        }
    }

    public double calculatePerimeter(int objectId) {
        GraphicObject object = objects.get(objectId);
        if (object == null) {
            System.out.println("No object found with ID: " + objectId);
            return 0;
        }

        if (object instanceof GroupObject) {
            double totalPerimeter = 0;
            GroupObject group = (GroupObject) object;
            for (GraphicObject member : group.getMembers()) {
                totalPerimeter += member.getPerimeter();
            }
            return totalPerimeter;
        } else {
            return object.getPerimeter();
        }
    }

    public double calculateAreaByType(String type) {
        Set<GraphicObject> uniqueObjects = new HashSet<>();

        objects.values().forEach(obj -> {
            if (obj instanceof GroupObject) {
                uniqueObjects.addAll(((GroupObject) obj).getMembers());
            } else {
                uniqueObjects.add(obj);
            }
        });

        return uniqueObjects.stream()
                .filter(obj -> obj.getType().equalsIgnoreCase(type))
                .mapToDouble(GraphicObject::getArea)
                .sum();
    }

    public double calculatePerimeterByType(String type) {
        Set<GraphicObject> uniqueObjects = new HashSet<>();

        objects.values().forEach(obj -> {
            if (obj instanceof GroupObject) {
                uniqueObjects.addAll(((GroupObject) obj).getMembers());
            } else {
                uniqueObjects.add(obj);
            }
        });

        return uniqueObjects.stream()
                .filter(obj -> obj.getType().equalsIgnoreCase(type))
                .mapToDouble(GraphicObject::getPerimeter)
                .sum();
    }

}
