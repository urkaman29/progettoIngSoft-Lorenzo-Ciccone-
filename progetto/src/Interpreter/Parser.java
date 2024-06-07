package Interpreter;

import java.io.IOException;
import java.io.StringReader;
import command.Command;
import composite.GraphicObjectManager;
import specificcommand.*;
import view.GraphicObjectPanel;
import java.util.*;

public class Parser {

    final GraphicObjectPanel gpanel;
    private GraphicObjectManager manager;

    private AnalizzatoreLessicale lexer;

    public Parser(String input, GraphicObjectPanel panel, GraphicObjectManager man) {
        lexer = new AnalizzatoreLessicale(new StringReader(input));

        this.gpanel = panel;
        this.manager = man;

    }

    public Command parse() throws IOException {
        Simboli token = lexer.prossimoSimbolo();
        Command command = null;

        switch (token) {
            case CREATE:
                command = parseCreateCommand(); //
                break;
            case DELETE:
                command = parseDeleteCommand(); //
                break;
            case MOVE:
                command = parseMoveCommand(); //
                break;
            case MOVE_OFFSET:
                command = parseMoveOffsetCommand(); //
                break;
            case SCALE:
                command = parseScaleCommand(); //
                break;
            case LIST:
                parseListCommand();
                break;
            case GROUP:
                command = parseGroupCommand(); //
                break;
            case UNGROUP:
                command = parseUngroupCommand();
                break;
            case AREA:
                parseAreaCommand();
                break;
            case PERIMETER:
                parsePerimeterCommand();
                break;
            default:
                System.out.println("Comando non riconosciuto.");

        }
        return command;
    }

    private Command parseCreateCommand() throws IOException {
        lexer.prossimoSimbolo();
        String objectType = lexer.getString();
        if ("circle".equalsIgnoreCase(objectType)) {
            return parseCreateCircleCommand();
        } else if ("img".equalsIgnoreCase(objectType)) {
            return parseCreateImgCommand();
        } else if ("rectangle".equalsIgnoreCase(objectType)) {
            return parseCreateRectangleCommand();
        } else {
            System.err.println("Tipo di oggetto non riconosciuto per il comando CREATE.");
            return null;
        }
    }

    private Command parseCreateRectangleCommand() throws IOException {
        lexer.prossimoSimbolo();
        double b = lexer.getNextDouble();
        lexer.prossimoSimbolo();
        double h = lexer.getNextDouble();
        lexer.prossimoSimbolo();
        lexer.prossimoSimbolo();
        double x = lexer.getNextDouble();
        lexer.prossimoSimbolo();
        double y = lexer.getNextDouble();

        CreateRectangleObject rectangle = new CreateRectangleObject(manager, gpanel, b, h, x, y);
        return rectangle;

    }

    private Command parseCreateImgCommand() throws IOException {
        lexer.prossimoSimbolo(); // Expect '('
        lexer.prossimoSimbolo();
        String img = lexer.getString();
        lexer.prossimoSimbolo(); // Expect ')'
        lexer.prossimoSimbolo(); // Expect '('
        double x = lexer.getNextDouble(); // Read x coordinate
        lexer.prossimoSimbolo(); // Expect ','
        double y = lexer.getNextDouble(); // Read y coordinate
        lexer.prossimoSimbolo(); // Expect ')'

        CreateImgCommand image = new CreateImgCommand(img, x, y, gpanel, manager);
        return image;
    }

    private Command parseCreateCircleCommand() throws IOException {
        lexer.prossimoSimbolo(); // Expect '('
        double radius = lexer.getNextDouble(); // Read radius
        lexer.prossimoSimbolo(); // Expect ')'
        lexer.prossimoSimbolo(); // Expect '('
        double x = lexer.getNextDouble(); // Read x coordinate
        lexer.prossimoSimbolo(); // Expect ','
        double y = lexer.getNextDouble(); // Read y coordinate
        lexer.prossimoSimbolo(); // Expect ')'

        CreateCircleCommand command = new CreateCircleCommand(radius, x, y, gpanel, manager);
        return command;
    }

    private Command parseDeleteCommand() throws IOException {
        int id = lexer.getNextInt();
        Command command = new DeleteCommand(manager, id);
        return command;
    }

    private Command parseGroupCommand() throws IOException {

        lexer.prossimoSimbolo(); // Expect '('
        List<Integer> ids = new ArrayList<>();
        int firstId = lexer.getNextInt(); // REad first Id
        ids.add(firstId);

        Simboli nextToken = lexer.prossimoSimbolo(); // Read ',' or ')'

        while (nextToken != Simboli.CLOSE_PARENTHESIS) {
            if (nextToken == Simboli.COMMA) {

                int nextId = lexer.getNextInt(); // Read next ID
                ids.add(nextId);
                nextToken = lexer.prossimoSimbolo(); // next token ',' or ')'

            } else {
                throw new IOException("Expected a comma but found: " + nextToken);
            }
        }
        Command groupCommand = new GroupCommand(manager, ids);
        return groupCommand;
    }

    private Command parseUngroupCommand() throws IOException {

        int gId = lexer.getNextInt();
        Command ungroupCommand = new UngroupCommand(manager, gId);
        return ungroupCommand;

    }

    private Command parseMoveCommand() throws IOException {
        int objectId = lexer.getNextInt();
        lexer.prossimoSimbolo(); // '('
        double x = lexer.getNextDouble(); // x
        lexer.prossimoSimbolo(); // ','
        double y = lexer.getNextDouble(); // y

        Command moveCommand = new MoveCommand(manager, objectId, x, y);
        return moveCommand;
    }

    private Command parseMoveOffsetCommand() throws IOException {
        int objectId = lexer.getNextInt();
        System.out.println(objectId);
        lexer.prossimoSimbolo();
        double offsetX = lexer.getNextDouble();
        lexer.prossimoSimbolo();
        double offsetY = lexer.getNextDouble();

        Command moveOffsetCommand = new MoveOffsetCommand(manager, objectId, offsetX, offsetY);
        return moveOffsetCommand;
    }

    private Command parseScaleCommand() throws IOException {
        int objectId = lexer.getNextInt();
        double scaleFactor = lexer.getNextDouble();

        Command scala = new ScaleCommand(manager, objectId, scaleFactor);
        return scala;
    }

    public String parseListCommand() throws IOException {
        lexer.prossimoSimbolo(); // 'ls'

        Simboli nextType = lexer.peekNextType();
        if (nextType == Simboli.NUMBER) {
            int objectId = lexer.getNextInt();
            return manager.listObjectDetails(objectId);
        }

        String specifier = lexer.getNextString();
        switch (specifier.toLowerCase()) {
            case "all":
                return manager.listAllObjects();
            case "groups":
                return manager.listAllGroups();
            case "circle":
                return manager.listObjectsByType("circle");
            case "rectangle":
                return manager.listObjectsByType("rectangle");
            default:
                return "Invalid specifier for list command: " + specifier;
        }
    }

    public void parseAreaCommand() throws IOException {
        Simboli nextType = lexer.peekNextType();
        Command command;

        if (nextType == Simboli.NUMBER) {
            int objectId = lexer.getNextInt();
            command = new AreaCommand(manager, objectId);
        } else {
            String type = lexer.getNextString();
            command = new AreaCommandByType(manager, type);
        }

        command.doIt();
    }

    private void parsePerimeterCommand() throws IOException {
        Simboli nextType = lexer.peekNextType();
        Command command;

        if (nextType == Simboli.NUMBER) {
            int objectId = lexer.getNextInt();
            command = new PerimeterCommand(manager, objectId);
        } else {
            String type = lexer.getNextString();
            command = new PerimeterCommandByType(manager, type);
        }

        command.doIt();
    }

}
