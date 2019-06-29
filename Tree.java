import gdp.stdlib.*;

/**
 * The Tree class implements an application that builds and 
 * outputs n-level pythagoras trees. Graphic output uses
 * stdlib's StdDraw and related functionality.
 * @author Wilhelm Simon
 * @version 1.0
 */
public class Tree {
	
	public static int n;
	private static int m;
	private static int i = 0;
	/**
	 * Main method initializes graphic output and executes
	 * the tree generation based on user input.
	 * @param args first argument is interpreted as an integer n representing the depth of the generated tree.
	 */
	public static void main(String args[])
	{
		if(args.length >= 1) { n = Integer.parseInt(args[0]); }
		else { n = 0; }
		if(n <= 0) {System.out.println("Nur positive ganze Zahlen gueltig"); System.exit(0);}
		
		Tree m = new Tree();
		
		StdDraw.setXscale(0, 10);
		StdDraw.setYscale(0, 10);
		
		m.BuildTree();
	}
	/**
	 * This method is the starting point of the tree generation. 
	 * Simply generates the first square in the bottom middle of 
	 * the output.
	 */
	private void BuildTree()
	{
		BuildSquare(4,0,1,0, n); //4051
	}
	/**
	 * Builds a square based on specified origin, length and angle. 
	 * Calls BuildTriangle after to generate the next points of
	 * origin.
	 * @param x0 x-coordinate of the point of origin
	 * @param y0 y-coordinate of the point of origin
	 * @param l length of the sides
	 * @param ext offset angle of the bottom side (and others accordingly)
	 * @param co layer counter - specified by user input n (see args param main method). Passed onto BuildTriangle to stop recursion when specified number of layer is reached.
	 */
	private void BuildSquare(double x0, double y0, double l, double ext, double co) //origin + inital length
	{
		/**/	
		//origin
		double ox = x0;
		double oy = y0;
		//temp
		double tx = 0;
		double ty = 0;
		
		double a = 0;
		
		double[] s = new double[8]; //counter-clockwise
		s[0] = ox;
		s[1] = oy;
		
		//x = cx + r * cos(a); cx origin, r = radius/length; a = angle in radians;
		//y = cy + r * sin(a); cy origin;
		
		//first point bottom right;
		a = Math.toRadians(0) + Math.toRadians(ext);
		tx = ox + (l * Math.cos(a));
		ty = oy + (l * Math.sin(a));
		
		s[2] = tx;
		s[3] = ty;
		//StdDraw.line(ox, oy, tx, ty); //bottom
		//System.out.println("ox: " + ox + "oy: " + oy + "tx: " + tx + "ty: " + ty);
		
		//diagonal; length different
		a = Math.toRadians(45) + Math.toRadians(ext);
		double ld = Math.sqrt(Math.pow(l, 2) + Math.pow(l, 2));
		tx = ox + (ld * Math.cos(a));
		ty = oy + (ld * Math.sin(a));
		
		s[4] = tx;
		s[5] = ty;
		//StdDraw.line(ox, oy, tx, ty);
		
		//top left
		a = Math.toRadians(90) + Math.toRadians(ext);
		tx = ox + (l * Math.cos(a));
		ty = oy + (l * Math.sin(a));
		
		s[6] = tx;
		s[7] = ty;
		//StdDraw.line(ox, oy, tx, ty);
		
		
		StdDraw.line(s[0], s[1], s[2], s[3]); //bottom
		StdDraw.line(s[2], s[3], s[4], s[5]); //right
		StdDraw.line(s[4], s[5], s[6], s[7]); //top
		StdDraw.line(s[6], s[7], s[0], s[1]); //left
		/**/
		BuildTriangle(s[6], s[7], l, ext, co-1);
	}
	/**
	 * Generates the x and y coordinates of the triangle tip based 
	 * on the associated square in the tree hierarchy. Calls
	 * BuildSquare for each new square with generated points 
	 * of origin.
	 * Stops recursion when number of specified layers is reached.
	 * 
	 * @param x0 x-coordinate of the origin
	 * @param y0 y-coordinate of the origin
	 * @param l length of the hypotenuse
	 * @param ext angle offset (see BuildSquare)
	 * @param co layer counter (see BuildSquare)
	 */
	private void BuildTriangle(double x0, double y0, double l, double ext, double co)
	{
		//System.out.println("co: " + co);
		if(co < 0) return;
		
		//System.out.println("ext; " + ext + " rad: " + Math.toRadians(ext));
		double[] t = new double[6];
		t[0] = x0;
		t[1] = y0;
		
		//x0,x1,y0,y1 als hypothenuse; a = 45; b = 45; c = 90	
		//min + random * (max + 1); int for convenience
		double alpha = 30 + (int)(Math.random() * ((30) + 1));
		double beta = 90 - alpha;
				
		//alpha = 45;
		//beta = 45;
		
		alpha = Math.toRadians(alpha);
		beta = Math.toRadians(beta);
		//System.out.println("alpha: " + alpha + " beta: " + beta + " rad: " + Math.toRadians(90));
		
		double gamma = Math.toRadians(90);
		//System.out.println("gamma: " + gamma);
		
		double c = l;
		double b = c * Math.sin(beta) / Math.sin(gamma);
		double a = c * Math.sin(alpha) / Math.sin(gamma);
		//System.out.println("c: " + c + "b: " + b + "a: " + a);
		
		//System.out.println(Math.sin(alpha) / a + " " + Math.sin(beta) / a + " " + Math.sin(gamma) / c);
		
		//bottom right (2nd point c) - special case
		double tx = t[0] + (c * Math.cos(0+Math.toRadians(ext)));
		double ty = t[1] + (c * Math.sin(0+Math.toRadians(ext)));
		t[2] = tx;
		t[3] = ty;
		//StdDraw.line(t[0], t[1], tx, ty);
		
		//tip of triangle
		tx = t[0] + (b * Math.cos(alpha+Math.toRadians(ext)));
		ty = t[1] + (b * Math.sin(alpha+Math.toRadians(ext)));
		t[4] = tx;
		t[5] = ty;
		
		//triangles dont get drawn, only squares
		//StdDraw.line(t[0], t[1], t[2], t[3]); //bottom
		//StdDraw.line(t[2], t[3], t[4], t[5]); //right
		//StdDraw.line(t[4], t[5], t[0], t[1]); //top
		
		BuildSquare(t[0],t[1],b,Math.toDegrees(alpha) + ext, co);
		BuildSquare(t[4],t[5],a,-Math.toDegrees(beta) + ext, co);
		
		//double[] arr = new double[] {t[4], t[5], l, ext};
		//return arr;
		//StdDraw.line(t[0], t[1], tx, ty);
	}
}
