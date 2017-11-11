package com.nexforttorch.flashclub;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;
import java.util.Random;

/*
    WallpaperService
        A wallpaper service is responsible for showing a live wallpaper behind applications that
        would like to sit on top of it. This service object itself does very little -- its only
        purpose is to generate instances of WallpaperService.Engine as needed. Implementing a
        wallpaper thus involves subclassing from this, subclassing an Engine implementation, and
        implementing onCreateEngine() to return a new instance of your engine.
*/
public class GradientLiveWallPaperService extends WallpaperService {
    private Context mContext;
    /*
        Handler
            A Handler allows you to send and process Message and Runnable objects associated with a
            thread's MessageQueue. Each Handler instance is associated with a single thread and that
            thread's message queue. When you create a new Handler, it is bound to the thread / message
            queue of the thread that is creating it -- from that point on, it will deliver messages
            and runnables to that message queue and execute them as they come out of the message queue.
    */
    private Handler mHandler = new Handler();
    private Random mRandom = new Random();
    private Point mSize = new Point();

    private int mInterval = 50;

    private int mIndicator;
    private boolean mRegenerateTopLayerPaint = true;
    private boolean mRegenerateBottomLayerPaint = true;
    private int mPaintTopLayerAlpha = 0;
    private Paint mPaintTopLayer = new Paint();
    private Paint mPaintBottomLayer = new Paint();

    private GradientManager mGradientManager;


    /*
        WallpaperService.Engine
            The actual implementation of a wallpaper. A wallpaper service may have multiple instances
            running (for example as a real wallpaper and as a preview), each of which is represented
            by its own Engine instance. You must implement onCreateEngine() to return your concrete
            Engine implementation.

        public abstract WallpaperService.Engine onCreateEngine ()
            Must be implemented to return a new instance of the wallpaper's engine. Note that
            multiple instances may be active at the same time, such as when the wallpaper is
            currently set as the active wallpaper and the user is in the wallpaper picker viewing
            a preview of it as well.
    */
    public Engine onCreateEngine(){
        return new GradientLiveWallpaperEngine();
    }

    // New private WallpaperService.Engine class started
    private class GradientLiveWallpaperEngine extends Engine{
        // To determine whether live wallpaper is visible state
        boolean mVisible = true;

        /*
            Runnable
                Represents a command that can be executed. Often used to run code in a different Thread.
        */
        Runnable mRunnable = new Runnable() {
            /*
                public abstract void run ()
                    Starts executing the active part of the class' code. This method is called when
                    a thread is started that has been created with a class which implements Runnable.
            */
            @Override
            public void run() {
                drawGradient();
            }
        };

        public GradientLiveWallpaperEngine(){
        }

        /*
            public void onDestroy ()
                Called right before the engine is going away. After this the surface will be
                destroyed and this Engine object is no longer valid.
        */
        @Override
        public void onDestroy() {
            super.onDestroy();
            mHandler.removeCallbacks(mRunnable);


        }

        /*
            public void onVisibilityChanged (boolean visible)
                Called to inform you of the wallpaper becoming visible or hidden. It is very
                important that a wallpaper only use CPU while it is visible..
        */
        @Override
        public void onVisibilityChanged(boolean visible){
            mVisible = visible;
            if (visible) {
                drawGradient();
            } else {
                mHandler.removeCallbacks(mRunnable);
            }
        }

        /*
            public void onSurfaceDestroyed (SurfaceHolder holder)
                Convenience for SurfaceHolder.Callback.surfaceDestroyed().

            public abstract void surfaceDestroyed (SurfaceHolder holder)
                This is called immediately before a surface is being destroyed. After returning from
                this call, you should no longer try to access this surface. If you have a rendering
                thread that directly accesses the surface, you must ensure that thread is no longer
                touching the Surface before returning from this function.
        */
        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder){
            super.onSurfaceDestroyed(holder);
            mVisible = false;
            mHandler.removeCallbacks(mRunnable);
        }

        // Custom method to draw a color pallet
        void drawGradient(){
            /*
                SurfaceHolder
                    Abstract interface to someone holding a display surface. Allows you to control
                    the surface size and format, edit the pixels in the surface, and monitor changes
                    to the surface. This interface is typically available through the SurfaceView class.
            */
            // Get the SurfaceHolder object
            SurfaceHolder holder = getSurfaceHolder();

            /*
                Canvas
                    The Canvas class holds the "draw" calls. To draw something, you need 4 basic
                    components: A Bitmap to hold the pixels, a Canvas to host the draw calls
                    (writing into the bitmap), a drawing primitive (e.g. Rect, Path, text, Bitmap),
                    and a paint (to describe the colors and styles for the drawing).
            */
            /*
                public abstract Canvas lockCanvas ()
                    Start editing the pixels in the surface. The returned Canvas can be used to draw
                    into the surface's bitmap. A null is returned if the surface has not been
                    created or otherwise cannot be edited. You will usually need to implement
                    Callback.surfaceCreated to find out when the Surface is available for use.
            */
            // Initialize the Canvas to draw something
            Canvas canvas = holder.lockCanvas();

            // Get the application context
            mContext = getApplicationContext();

            // Set the point x y values
            mSize.x = canvas.getWidth();
            mSize.y = canvas.getHeight();

            // Initialize a new instance of GradientManager class
            mGradientManager = new GradientManager(mContext,mSize);

            try{
                /*
                    We will draw canvas with two layers, top and bottom
                    Top layer continuously change its transparency
                    Top layer transparency cycle is 0 > 255 > 0

                    When top layer transparency reach 255
                        then we will convert it again zero
                        Replace the bottom layer shader with top layer shader
                        Regenerate the top layer shader
                */

                // If required regenerate the top layer shader
                if(mRegenerateTopLayerPaint){
                    /*
                        setDither(boolean dither)
                            Helper for setFlags(), setting or clearing the DITHER_FLAG bit Dithering
                            affects how colors that are higher precision than the device are down-sampled.
                    */
                    mPaintTopLayer.setDither(true);
                    mIndicator = mRandom.nextInt(3);
                    // Assign the random gradient type gradient
                    if(mIndicator == 0){
                        mPaintTopLayer.setShader(mGradientManager.getRandomLinearGradient());
                    }else if(mIndicator == 1){
                        mPaintTopLayer.setShader(mGradientManager.getRandomRadialGradient());
                    }else {
                        mPaintTopLayer.setShader(mGradientManager.getRandomSweepGradient());
                    }
                }

                // If required regenerate the bottom layer shader
                if(mRegenerateBottomLayerPaint){
                    mPaintBottomLayer.setDither(true);
                    mIndicator = mRandom.nextInt(3);
                    // Assign the random gradient type gradient
                    if(mIndicator == 0){
                        /*
                            Paint
                                The Paint class holds the style and color information about how to
                                draw geometries, text and bitmaps.

                            setShader(Shader shader)
                                Set or clear the shader object.

                            Shader
                                Shader is the based class for objects that return horizontal spans
                                of colors during drawing. A subclass of Shader is installed in a
                                Paint calling paint.setShader(shader). After that any object
                                (other than a bitmap) that is drawn with that paint will get its
                                color(s) from the shader.
                        */
                        mPaintBottomLayer.setShader(mGradientManager.getRandomLinearGradient());
                    }else if(mIndicator == 1){
                        mPaintBottomLayer.setShader(mGradientManager.getRandomRadialGradient());
                    }else {
                        mPaintBottomLayer.setShader(mGradientManager.getRandomSweepGradient());
                    }
                }


                /*
                    setAlpha(int a)
                        Helper to setColor(), that only assigns the color's alpha value, leaving
                        its r,g,b values unchanged.
                */
                // Set the top layer alpha
                mPaintTopLayer.setAlpha(mPaintTopLayerAlpha);

                if(mPaintTopLayerAlpha <256){
                    mPaintTopLayerAlpha ++;
                    mRegenerateTopLayerPaint = false;
                    mRegenerateBottomLayerPaint = false;
                }

                /*
                    drawRect(float left, float top, float right, float bottom, Paint paint)
                        Draw the specified Rect using the specified paint.
                */
                // Finally, draw the canvas with two layer
                canvas.drawRect(0,0,mSize.x,mSize.y,mPaintBottomLayer);
                canvas.drawRect(0,0,mSize.x,mSize.y,mPaintTopLayer);

                if (mPaintTopLayerAlpha == 256)
                {
                    /*
                        Rule - To generate continuous animation

                            top layer alpha = 0 (full transparent)
                            top layer = null (regenerate)
                            bottom layer shader = top layer shader
                    */
                    mPaintTopLayerAlpha = 0;
                    mPaintBottomLayer.setShader(mPaintTopLayer.getShader());
                    mRegenerateTopLayerPaint = true;
                }

            }finally{
                if (canvas != null){
                    /*
                        public abstract void unlockCanvasAndPost (Canvas canvas)
                            Finish editing pixels in the surface. After this call, the surface's
                            current pixels will be shown on the screen, but its content is lost, in
                            particular there is no guarantee that the content of the Surface will
                            remain unchanged when lockCanvas() is called again.

                            Parameters
                                canvas : The Canvas previously returned by lockCanvas().
                    */
                    holder.unlockCanvasAndPost(canvas);
                }
            }

            /*
                removeCallbacks(Runnable r)
                    Remove any pending posts of Runnable r that are in the message queue.
            */
            // Now remove the call backs from handler
            mHandler.removeCallbacks(mRunnable);

            // If visible continue the animation
            if(mVisible){
                /*
                    postDelayed(Runnable r, long delayMillis)
                        Causes the Runnable r to be added to the message queue, to be run after the
                        specified amount of time elapses.
                */
                mHandler.postDelayed(mRunnable, mInterval);
            }
        }
    } // WallpaperService.Engine class end
}// WallpaperService class end
