package net.iskandar.alarmclock.tools;

import java.util.Formatter;
import java.util.Random;

/**
 * Created by iskandar on 10/17/14.
 */
public class Excercise {

    public enum Op {
        PLUS('+'),
        MINUS('-'),
        MULTIPLY('*'),
        DIVIDE('/');

        private char lex;

        Op(char lexem) {
            this.lex = lexem;
        }

        char lexem(){
            return lex;
        }
    }

    private int left;
    private int right;
    private int result;
    private Op op;

    public Excercise() {
        op = Op.values()[random.nextInt(4)];
        switch(op){
            case PLUS:
                left = random.nextInt(100) + 1;
                right = random.nextInt(100) + 1;
                result = left + right;
                break;
            case MINUS:
                right = random.nextInt(70) + 1;
                result = random.nextInt(30) + 1;
                left = right + result;
                break;
            case MULTIPLY:
                left = random.nextInt(60) + 1;
                right = random.nextInt(12) + 1;
                result = left * right;
                break;
            case DIVIDE:
                right = random.nextInt(20) + 1;
                result = random.nextInt(20) + 1;
                left = right * result;
                break;
        }


    }

    private static final Random random = new Random();

    static {
        random.setSeed(System.currentTimeMillis());
    }


    public int getResult(){
        return result;
    }

    public String format(boolean includeResult){
        Formatter fmt = new Formatter();
        if(includeResult)
            fmt.format("%d %c %d = %d", left, op.lexem(), right, getResult());
        else
            fmt.format("%d %c %d = ", left, op.lexem(), right);
        return fmt.toString();
    }

    public static void main(String[] args){
        for(int i = 0; i < 100; i++) {
            Excercise ex = new Excercise();
            System.out.println(ex.format(true));
        }

    }


}
