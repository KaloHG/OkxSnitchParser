package moe.kayla.okxparser;

public class Snitch {
    int x;
    int y;
    int z;

    String name;
    String group;

    public Snitch(int x,int y,int z,String name,String group) {
        this.x = x;
        this.y = y;
        this.z = z;

        this.name = name;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getGroup() {
        return group;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }
}
