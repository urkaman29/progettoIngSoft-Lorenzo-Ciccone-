package Interpreter;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.awt.geom.Point2D;

public class AnalizzatoreLessicale {

    private StreamTokenizer input;
    private Simboli simbolo;

    public AnalizzatoreLessicale(Reader in) {
        input = new StreamTokenizer(in);
        input.resetSyntax();
        input.wordChars('a', 'z');
        input.wordChars('A', 'Z');
        input.wordChars('0', '9');
        input.whitespaceChars('\u0000', ' ');
        input.ordinaryChar('(');
        input.ordinaryChar(')');
        input.ordinaryChar(',');
        input.parseNumbers();
        input.quoteChar('"');
    }

    public String getString() {
        return input.sval;
    }

    public Simboli prossimoSimbolo() {
        try {
            switch (input.nextToken()) {
                case StreamTokenizer.TT_EOF:
                    simbolo = Simboli.EOF;
                    break;
                case StreamTokenizer.TT_WORD:
                    String word = input.sval;
                    if (word.equalsIgnoreCase("create"))
                        simbolo = Simboli.CREATE;
                    else if (word.equalsIgnoreCase("del"))
                        simbolo = Simboli.DELETE;
                    else if (word.equalsIgnoreCase("mv"))
                        simbolo = Simboli.MOVE;
                    else if (word.equalsIgnoreCase("mvoff"))
                        simbolo = Simboli.MOVE_OFFSET;
                    else if (word.equalsIgnoreCase("scale"))
                        simbolo = Simboli.SCALE;
                    else if (word.equalsIgnoreCase("ls"))
                        simbolo = Simboli.LIST;
                    else if (word.equalsIgnoreCase("grp"))
                        simbolo = Simboli.GROUP;
                    else if (word.equalsIgnoreCase("ungrp"))
                        simbolo = Simboli.UNGROUP;
                    else if (word.equalsIgnoreCase("area"))
                        simbolo = Simboli.AREA;
                    else if (word.equalsIgnoreCase("perimeter"))
                        simbolo = Simboli.PERIMETER;
                    else
                        simbolo = Simboli.WORD;
                    break;
                case '"':
                    simbolo = Simboli.STRING;
                    break;
                case '(':
                    simbolo = Simboli.OPEN_PARENTHESIS;
                    break;
                case ')':
                    simbolo = Simboli.CLOSE_PARENTHESIS;
                    break;
                case ',':
                    simbolo = Simboli.COMMA;
                    break;
                default:
                    simbolo = Simboli.INVALID_CHAR;
            }
        } catch (IOException e) {
            simbolo = Simboli.EOF;
        }
        return simbolo;
    }

    public Simboli peekNextType() throws IOException {
        int nextToken = input.nextToken();
        input.pushBack(); // Torna indietro di un token
        switch (nextToken) {
            case StreamTokenizer.TT_NUMBER:
                return Simboli.NUMBER;
            case StreamTokenizer.TT_WORD:
                return Simboli.WORD;
            case '(':
                return Simboli.OPEN_PARENTHESIS;
            case ')':
                return Simboli.CLOSE_PARENTHESIS;
            case ',':
                return Simboli.COMMA;
            case StreamTokenizer.TT_EOF:
                return Simboli.EOF;
            default:
                return Simboli.INVALID_CHAR;
        }
    }

    public double getNextDouble() throws IOException {
        if (input.nextToken() == StreamTokenizer.TT_NUMBER) {
            return input.nval;
        } else {
            throw new IOException("Expected a number, found " + input.toString());
        }
    }

    public String getNextString() throws IOException {
        if (input.nextToken() == StreamTokenizer.TT_WORD) {
            return input.sval;
        } else {
            throw new IOException("Expected a string, found " + input.toString());
        }
    }

    public int getNextInt() throws IOException {
        int nextToken = input.nextToken();
        if (nextToken == StreamTokenizer.TT_NUMBER) {
            int num = (int) input.nval; // Assicurati che il valore possa essere convertito senza perdita di
                                        // informazione
            if (input.nval == (double) num) {
                return num;
            } else {
                throw new IOException("Expected an integer, found a non-integer number: " + input.nval);
            }
        } else {
            throw new IOException("Expected a number, found token type: " + nextToken);
        }
    }

    public Point2D getNextPoint() throws IOException {
        if (input.nextToken() != '(') {
            throw new IllegalArgumentException("Expected '(' at the start of a point definition.");
        }

        double x = getNextDouble(); // Leggi la coordinata x

        if (input.nextToken() != ',') {
            throw new IllegalArgumentException("Expected ',' between coordinates.");
        }

        double y = getNextDouble(); // Leggi la coordinata y

        if (input.nextToken() != ')') {
            throw new IllegalArgumentException("Expected ')' at the end of a point definition.");
        }

        Point2D position = new Point2D.Double(x, y);
        return position;
    }

}
