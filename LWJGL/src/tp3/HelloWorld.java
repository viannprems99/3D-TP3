package tp3;

import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class HelloWorld {

	// The window handle
	private long window;
    private float rotate;

	public void run() {
		System.out.println("Hello LWJGL " + Version.getVersion() + "!");

		init();
		loop();

		// Free the window callbacks and destroy the window
		glfwFreeCallbacks(window);
		glfwDestroyWindow(window);

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback(null).free();
	}

	private void init() {
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint(System.err).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() )
			throw new IllegalStateException("Unable to initialize GLFW");

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        int WIDTH = 800;
        int HEIGHT = 600;

		// Create the window
		window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
		if ( window == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
		});

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt(1); // int*
			IntBuffer pHeight = stack.mallocInt(1); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize(window, pWidth, pHeight);

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			// Center the window
			glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent(window);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(window);
        
	}

	private void loop() {
		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();

		// Set the clear color
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        rotate = 0.0f;

		// Run the rendering loop until the user has attempted to close
		// the window or has pressed the ESCAPE key.
		while ( !glfwWindowShouldClose(window) ) {
			
            glMatrixMode(GL_PROJECTION);
            //glPushMatrix();
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT); // clear the framebuffer

        
            // Remove last render :
            glLoadIdentity();

            // Apply a translation to the current position :
            glTranslatef(0.0f, 0.0f, -1.0f);

            glMatrixMode(GL_MODELVIEW);

            // Apply a rotation to the current position :
            glRotatef(rotate, 1.0f, 0.0f, 0.0f);
            glRotatef(rotate, 0.0f, 1.0f, 0.0f);
            glRotatef(rotate, 0.0f, 0.0f, 1.0f);

            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

            // Draw a Cube :
            glBegin(GL_QUADS); // Start Drawing The Cube
            
                glColor3f(1.0f,0.0f,0.0f); //red 
                glVertex3f(1.0f, 1.0f, -1.0f); // Top Right Of The Quad (Top)
                glVertex3f( -1.0f, 1.0f, -1.0f); // Top Left Of The Quad (Top)
                glVertex3f( -1.0f, 1.0f, 1.0f ); // Bottom Left Of The Quad (Top)
                glVertex3f( 1.0f, 1.0f, 1.0f ); // Bottom Right Of The Quad (Top)
                    
                glColor3f( 0.0f,1.0f,0.0f ); //green 
                glVertex3f( 1.0f, -1.0f, 1.0f ); // Top Right Of The Quad
                glVertex3f( -1.0f, -1.0f, 1.0f ); // Top Left Of The Quad
                glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
                glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad 

                glColor3f( 0.0f,0.0f,1.0f ); //blue 
                glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Front)
                glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Left Of The Quad (Front)
                glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
                glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 

                glColor3f( 1.0f,1.0f,0.0f ); //yellow
                glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
                glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
                glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Back)
                glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Back)

                glColor3f( 1.0f,0.0f,1.0f ); //purple
                glVertex3f( -1.0f, 1.0f, 1.0f ); // Top Right Of The Quad (Left)
                glVertex3f( -1.0f, 1.0f, -1.0f ); // Top Left Of The Quad (Left)
                glVertex3f( -1.0f, -1.0f, -1.0f ); // Bottom Left Of The Quad
                glVertex3f( -1.0f, -1.0f, 1.0f ); // Bottom Right Of The Quad 

                glColor3f( 0.0f,1.0f, 1.0f ); //sky blue
                glVertex3f( 1.0f, 1.0f, -1.0f ); // Top Right Of The Quad (Right)
                glVertex3f( 1.0f, 1.0f, 1.0f ); // Top Left Of The Quad
                glVertex3f( 1.0f, -1.0f, 1.0f ); // Bottom Left Of The Quad
                glVertex3f( 1.0f, -1.0f, -1.0f ); // Bottom Right Of The Quad
            
            glEnd(); // Done Drawing The Quad
            //glPopMatrix();

            // Increase rotation :
            rotate += 0.2f;

			glfwSwapBuffers(window); // swap the color buffers

			// Poll for window events. The key callback above will only be
			// invoked during this call.
			glfwPollEvents();
		}
	}

	public static void main(String[] args) {
		new HelloWorld().run();
	}

}