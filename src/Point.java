public class Point {
    private int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void takeRandomStep() {
        int rand = (int)(Math.random()*4);
        if (rand==0) {
            this.x += 3;
        }

        else if (rand==1) {
            this.y += 3;
        }

        else if (rand==2) {
            this.x -= 3;
        }

        else {
            this.y -= 3;
        }
    }
}
