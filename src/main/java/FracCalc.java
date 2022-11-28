/** Fraction Calculator Program
 *  w/ Simplification
 * @author Jake Abendroth
 * @version 1.4
 * @since 2022-11-28
 */
import java.math.BigInteger;
import java.util.Objects;
import java.util.Scanner;
public class FracCalc {
    /**
     * Main method asks for String input to be passed to produceAnswer,
     * then prints result of produceAnswer
     */
    public static void main(String[] args) 
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter equation: ");
        System.out.println(produceAnswer(input.nextLine()));
        input.close();
    }

    /**
     * Calculator processes input, and returns answer to equation
     * @param input the (String)equation to calculate
     * @return answer
     */
    public static String produceAnswer(String input)
    {
        String op1 = null;
        String op2 = null;
        String operation = null;
        boolean p = false;
        boolean done = false;
        // Separation of input into terms
        for(int i = 0; i < input.length(); i++) {
            if(input.charAt(i) == ' ' && !done) {
                op1 = input.substring(0, i);
                p = true;
                done = true;
            }
            if(input.charAt(i) == ' ' && p) {
                operation = input.substring(i-1, i);
                op2 = input.substring(i+1);
            }
        }
        int whole = 0;
        int num = 0;
        int den = 1;
        int whole1 = 0;
        int num1 = 0;
        int den1 = 1;
        // Term 1 into individual integers
        int a = op1.indexOf("_");
        int b = op1.indexOf("/");
        if(a > -1 && b > -1) {
            whole1 = Integer.parseInt(op1.substring(0, a));
            num1 = Integer.parseInt(op1.substring(a+1, b));
            den1 = Integer.parseInt(op1.substring(b+1));
          if(whole1 < 0) {
            num1 *= -1; } }
        if(a == -1 && b > -1) {
            num1 = Integer.parseInt(op1.substring(0, b));
            den1 = Integer.parseInt(op1.substring(b+1));
        }
        if(b == -1) {
            whole1 = Integer.parseInt(op1);
        }
        // Term 2 into individual integers
        int x = op2.indexOf("_");
        int y = op2.indexOf("/");
        if(x > -1 && y > -1) {
            whole = Integer.parseInt(op2.substring(0, x));
            num = Integer.parseInt(op2.substring(x+1, y));
            den = Integer.parseInt(op2.substring(y+1));
            if(whole < 0) {
                num *= -1;
        }
          }
        if(x == -1 && y > -1) {
            num = Integer.parseInt(op2.substring(0, y));
            den = Integer.parseInt(op2.substring(y+1));
        }
        if(y == -1) {
            whole = Integer.parseInt(op2);
        }
        int numer1 = 0;
        int numer2 = 0;
        if(whole1 != 0) {
          numer1 = toImproperNum(whole1, num1, den1);
        } else {
          numer1 = num1;
        }
        if(whole != 0) {
          numer2 = toImproperNum(whole, num, den);
        } else {
          numer2 = num;
        }
        
        int numerator;
        int denom = 1;
        // Reading operation and calculating accordingly
        switch(Objects.requireNonNull(operation)) {
            case "+":
                if(den == den1) {
                    numerator = numer1 + numer2;
                    denom = den;
                }
                else {
                    numerator = ((numer1 * den) + (numer2 * den1));
                    denom = den * den1;
                }
                break;
            case "-":
                numerator = ((numer1 * den) - (numer2 * den1));
                denom = den * den1;
                break;
            case "*":
                numerator = (numer1 * numer2);
                denom = den * den1;
                break;
            case "/":
                if(numer1 < 0) {
                    den1 *= -1;
                }
                if(numer2 < 0) {
                    den *= -1;
                }
                numerator = (numer1*den);
                denom = den1 * numer2;
                break;
            default:
                return "Invalid!";
        }
        // Improper fraction simplification
        int w = 0;
        boolean mix = false;
        if(denom < 0) {
            denom *= -1;

        }
        if(Math.abs(numerator) > denom) {
            w = (int)(numerator / denom);
            numerator = numerator % denom;
            mix = true;
        }
      // Greatest common denominator finder
      BigInteger n = BigInteger.valueOf(numerator);
      BigInteger d = BigInteger.valueOf(denom);
      BigInteger gcd = n.gcd(d);
      numerator /= gcd.intValue();
      denom /= gcd.intValue();
      // Output forms
        if(mix && numerator != 0) {
            return w+"_"+Math.abs(numerator)+"/"+denom;
        } else if(mix && numerator == 0) {
          return ""+w;
        }
        if(numerator == denom && !mix) {
            return "1";
        }
        if(numerator % denom == 0 && !mix) {
            return ""+numerator/denom;
        }
        if(numerator < denom) {
          return numerator+"/"+denom;
      } else {
          return "oops"; }
    }

    /**
     * Converts a mixed number to an improper fraction
     * @param w Whole number
     * @param n Numerator
     * @param d Denominator
     * @return Improper fraction's new numerator
     */
    public static int toImproperNum(int w, int n, int d) {
        return ((w*d)+n);
    }
}