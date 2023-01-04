import java.util.List;

abstract class FIGURE {
    protected float height;
    public float square() {
        return 0f;
    }
}

class CIRCLE extends FIGURE{
    public CIRCLE(float diameter) {
        this.height = diameter;
    }
    @Override
    public float square() {
        return (float) Math.PI * (this.height * this.height)/4;
    }
}
class RECTANGLE extends FIGURE{
    protected float width;

    public RECTANGLE(float height, float width) {
        this.height = height;
        this.width = width;
    }
    @Override
    public float square() {
        return this.width * this.height;
    }
}

class SQUARE extends RECTANGLE {
    public SQUARE(float height) {
        super(height, height);
    }
}

class TESTER {
    public String name_class;
    public float[] args;
    public float square;
    public TESTER(String name_class, float[] args, float square) {
        this.name_class = name_class;
        this.args = args;
        this.square = square;
    }
    public boolean go_test(){
        boolean flag = false;
        if (name_class == "RECTANGLE") {
            flag = Math.abs(this.args[0] * this.args[1] - square) < 0.001;
        } else if (name_class == "CIRCLE") {
            flag = Math.abs(Math.PI * this.args[0] * this.args[0]/4 - square) < 0.001f;
        } else if (name_class == "SQUARE") {
            flag = Math.abs(this.args[0] * this.args[0] - square) < 0.001;
        }
        if (flag) {
            System.out.println(this.name_class + "test+");
        } else {
            System.out.println("! "+this.name_class + "test-");
        }
        return flag;
    }
}

class Scratch {
    public static void main(String[] args) {
        RECTANGLE X = new RECTANGLE(10, 23);
        System.out.println(X.square());

        SQUARE Y = new SQUARE(11);
        System.out.println(Y.square());

        CIRCLE Z = new CIRCLE(6);
        System.out.println(Z.square());

        float[] r_args1 = {10, 23};
        TESTER r1 = new TESTER("RECTANGLE", r_args1, 230);
        float[] r_args2 = {8, 7};
        TESTER r2 = new TESTER("RECTANGLE", r_args2, 56);

        float[] c_args1 = {10};
        TESTER c1 = new TESTER("CIRCLE", c_args1, 78.5398f);
        float[] c_args2 = {180};
        TESTER c2 = new TESTER("CIRCLE", c_args2, 25446.90049f);

        float[] s_args1 = {11};
        TESTER s1 = new TESTER("SQUARE", s_args1, 121);
        float[] s_args2 = {3};
        TESTER s2 = new TESTER("SQUARE", s_args2, 9);
        r1.go_test();
        r2.go_test();
        c1.go_test();
        c2.go_test();
        s1.go_test();
        s2.go_test();
    }
}
