import javax.script.*;
import java.io.*;

public class RunJS {
    public static class IO {
        private final ScriptEngine engine;
        public IO(ScriptEngine engine) {
            this.engine = engine;
        }

        public void print(String... message) {
            for (int i = 0; i < message.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(message[i]);
            }
        }

        public void println(String... message) {
            print(message);
            System.out.println();
        }

        public void include(String file) throws Exception {
            engine.eval(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        }
    }
    public static void main(String[] args) throws ScriptException, IOException {
        ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        engine.put("io", new IO(engine));
        engine.eval(
            "println = function() { io.println(Array.prototype.slice.call(arguments)); };" + 
            "print   = function() { io.print  (Array.prototype.slice.call(arguments)); };" +
            "include = function(file) { io.include(file); }"
        );

        engine.eval(new InputStreamReader(new FileInputStream("expression.js"), "UTF-8"));
    }
}