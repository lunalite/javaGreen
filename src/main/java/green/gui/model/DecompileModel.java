package green.gui.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecompileModel {

    private StringProperty code = new SimpleStringProperty();
    private StringProperty opCode = new SimpleStringProperty();
    private StringProperty movement = new SimpleStringProperty();
    private StringProperty comment = new SimpleStringProperty();

    public DecompileModel(){}

    public DecompileModel(StringProperty code, StringProperty opCode, StringProperty movement, StringProperty comment) {
        this.code = code;
        this.opCode = opCode;
        this.movement = movement;
        this.comment = comment;
    }

    public DecompileModel(String _decompiled, boolean isInstruction) {
        Pattern p;
        Matcher m;
        if (_decompiled != null) {
            //Check regex
            p = Pattern.compile("^ +\\d+: \\w+ .*//.*$");
            m = p.matcher(_decompiled);
            if (!isInstruction) {
                //TODO: Combine the columns into a single column
                this.code = new SimpleStringProperty(null);
                this.opCode = new SimpleStringProperty(null);
                this.movement = new SimpleStringProperty(null);
                this.comment = new SimpleStringProperty(_decompiled);
            } else {
                _decompiled = _decompiled.replaceAll("[ ]{2,}", " ");
                _decompiled = _decompiled.replaceAll("^.(?=\\d+?: )", "");

                //Check for code regex
                p = Pattern.compile("\\d+(?=: )");
                m = p.matcher(_decompiled);
                if (m.find()) {
                    this.code = new SimpleStringProperty(m.group(0));
                } else {
                    this.code = new SimpleStringProperty("1");
                }

                //Check for comments regex
                if (_decompiled.contains("//")) {
                    // Check for opcode movement regex
                    p = Pattern.compile("(?<=: ).*(?= //)");
                    m = p.matcher(_decompiled);
                    if (m.find()) {
                        String[] opMove = m.group(0).split(" ", 2);
                        this.opCode = new SimpleStringProperty(opMove[0]);
                        this.movement = new SimpleStringProperty((opMove.length > 1) ? opMove[1] : "");
                    } else {
                        this.movement = new SimpleStringProperty("");
                        this.opCode = new SimpleStringProperty("");
                    }

                    p = Pattern.compile("//.+");
                    m = p.matcher(_decompiled);
                    if (m.find()) {
                        this.comment = new SimpleStringProperty(m.group(0));
                    } else {
                        this.comment = new SimpleStringProperty("");
                    }
                } else {
                    //Check for opcode movement regex if no comments
                    p = Pattern.compile("(?<=: ).*(?=$)");
                    m = p.matcher(_decompiled);
                    if (m.find()) {
                        String[] opMove = m.group(0).split(" ", 2);
                        if (opMove[0].matches("^[0-9]*")) {
                            this.opCode = new SimpleStringProperty(this.code.getValue().toString());
                            this.code = new SimpleStringProperty("");
                            this.movement = new SimpleStringProperty(opMove[0]);

                        } else {
                            this.opCode = new SimpleStringProperty(opMove[0]);
                            this.movement = new SimpleStringProperty((opMove.length > 1) ? opMove[1] : "");
                        }
                    } else {
                        this.movement = new SimpleStringProperty("");
                        this.opCode = new SimpleStringProperty("");
                    }
                    this.comment = new SimpleStringProperty("");
                }
//                exit(0);
            }
        } else {
            this.code = new SimpleStringProperty("");
            this.opCode = new SimpleStringProperty("");
            this.movement = new SimpleStringProperty("");
            this.comment = new SimpleStringProperty("");
        }
    }

    public String getCode() {
        return this.code.get();
    }

    public void setCode(String code) {
        this.code.set(code);
    }

    public StringProperty codeProperty() {
        return this.code;
    }

    public String getOpCode() {
        return opCode.get();
    }

    public StringProperty opCodeProperty() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode.set(opCode);
    }

    public String getMovement() {
        return movement.get();
    }

    public StringProperty movementProperty() {
        return movement;
    }

    public void setMovement(String movement) {
        this.movement.set(movement);
    }

    public String getComment() {
        return comment.get();
    }

    public StringProperty commentProperty() {
        return comment;
    }

    public void setComment(String comments) {
        this.comment.set(comments);
    }
}
