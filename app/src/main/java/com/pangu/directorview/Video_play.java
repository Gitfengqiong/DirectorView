package com.pangu.directorview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import java.io.IOException;

import static com.pangu.directorview.MainView.test;

public class Video_play {

    public  static IjkMediaPlayer ijkMediaPlayers[] =new IjkMediaPlayer[4];



    public void play_on(int viewnum  , final SurfaceHolder surfaceHolder[] , final String Uri[] ) {


        IjkMediaPlayer.loadLibrariesOnce(null);
       IjkMediaPlayer.native_profileBegin("libijkplayer.so");

        for (int i = 0; i < viewnum; i++) {

            final int finalI = i;
            final IjkMediaPlayer  mediaPlayer = new IjkMediaPlayer();//直接new个IjkMediaPlayer
             ijkMediaPlayers[finalI] = mediaPlayer ;
            new Thread() {
                @Override
                public void run() {
                    super.run();


                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "framedrop", 60);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "max-fps", 30);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "fps", 30);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_CODEC, "skip_loop_filter", 48);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "overlay-format", IjkMediaPlayer.SDL_FCC_YV12);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "packet-buffering", 0);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "fflags", "nobuffer");
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "max-buffer-size", 1024);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "min-frames", 3);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "start-on-prepared", 1);
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "probsize", "4096");
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "analyzeduration", "2000000");
                    mediaPlayer .setOption(IjkMediaPlayer.OPT_CATEGORY_FORMAT, "rtsp_transport", "tcp");
                    mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
                    mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"framedrop",5);
                    mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER,"reconnect",5);

                //    mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-auto-rotate", 1);

                    mediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec-handle-resolution-change", 1);


                    mediaPlayer.setScreenOnWhilePlaying(true);



                    try {
                        //  mediaPlayer.setDataSource("/storage/emulated/0/v1.mp4");
                        mediaPlayer .setDataSource(Uri[finalI]);
                        //   mediaPlayer.setDataSource("rtsp://192.168.0.34:8554/1");"rtmp://192.168.0.34/live/x4"
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer .prepareAsync();
                    mediaPlayer .start();

                    ijkMediaPlayers[finalI].setVolume(0f,0f);

                    surfaceHolder[finalI].addCallback(new SurfaceHolder.Callback() {
                        @Override
                        public void surfaceCreated(SurfaceHolder holder) {

                            mediaPlayer.setDisplay( surfaceHolder[finalI]);

                        }

                        @Override
                        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                        }

                        @Override
                        public void surfaceDestroyed(SurfaceHolder holder) {

                            mediaPlayer.stop();


                        }
                    });
                    if(test) {

                    }
                }
            }.start();
            synchronized (this){
                try {
                    wait(100);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
