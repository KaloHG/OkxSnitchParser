package moe.kayla.okxparser;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParserMain {

    public static ParserMain instance;

    public HashMap<String, Snitch> alts = new HashMap<>();

    Pattern registryPattern = Pattern.compile("([A-Za-z0-9_]{2,16}) is at \\[(-?[0-9]+), (-?[0-9]+), (-?[0-9]+)\\] \\[(\\S*)\\] _\\((\\S*)\\)_");

    public static void main(String[] args) {
        instance = new ParserMain();
        instance.run(args[0], args[1]);
    }

    public void run(String file, String output) {
        try {
            FileInputStream fs = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fs));
            String strLine;
            //Read line by line
            while ((strLine = br.readLine()) != null) {
                Matcher matcher = registryPattern.matcher(strLine);
                if(matcher.find()) {
                    int x = Integer.parseInt(matcher.group(2));
                    int y = Integer.parseInt(matcher.group(3));
                    int z = Integer.parseInt(matcher.group(4));

                    String name = matcher.group(6);
                    String group = matcher.group(5);
                    Snitch snitch = new Snitch(x, y, z, name, group);
                    if(alts.get(name) != null) { continue; }
                    System.out.println("SNITCH: " + snitch.getName());
                    alts.put(name, snitch);
                }
            }
            System.out.println("Total Lines Read: " + br.lines().count());
            output(output);
            fs.close();
        } catch (Exception ex) {
            System.err.println("Err" + ex.getMessage());
        }
    }

    public void output(String outfile) {
        System.out.println("Preparing to output...");
        System.out.println("Objects to output: " + alts.size());
        try {

            String linefrominput = "";
            int runs = 0;
            for(Snitch s : alts.values()) {
                System.out.println("Prepping snitch object for " + runs + " snitch");
                linefrominput = linefrominput + s.getX() + "," + s.getY() + "," + s.getZ() + ",world,jalist," + s.getGroup() + "," + s.getName() + ",400,entry,,\n";
                runs++;
            }
            System.out.println("Snitches Printed: " + alts.size());
            PrintWriter out = new PrintWriter(new FileWriter(outfile));

            System.out.println("Outputting console log");
            out.println(linefrominput);

            out.close();
        } catch (Exception ex) {
            System.out.println("Failed to write: " + ex.getMessage());
        }
    }
}
