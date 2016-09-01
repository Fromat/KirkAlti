package engine;

import java.util.HashMap;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {

    static private SoundManager _instance;
    private static SoundPool mSoundPool;
    private static HashMap<Integer, Integer> mSoundPoolMap;
    private static AudioManager  mAudioManager;
    private static Context mContext;

    int key;

    public SoundManager(Context ctx){
        initSounds(ctx);
    }

    /**
     * Initialize the storage for the sounds
     *
     * @param theContext The Application context
     */
    public void initSounds(Context theContext) {
        mContext = theContext;
        mSoundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
        mSoundPoolMap = new HashMap<Integer, Integer>();
        mAudioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
    }

    /**
     * Add a new Sound to the SoundPool
     *
     * @param Index - The Sound Index for Retrieval
     * @param SoundID - The Android ID for the Sound asset.
     */
    public void addSound(int Index,int SoundID) {
        mSoundPoolMap.put(Index, mSoundPool.load(mContext, SoundID, 1));
    }

    public int loadSound(int id) {
        int value = mSoundPool.load(mContext, id, 1);
        mSoundPoolMap.put(key++, value);
        return value;
    }

    /**
     * Play a Sound
     *
     * @param index - The Index of the Sound to be played
     * @param speed - The Speed to play not, not currently used but included for compatibility
     */
    public void playSound(int soundId,float speed) {
        float streamVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        streamVolume = streamVolume / mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        //mSoundPool.play(mSoundPoolMap.get(index), streamVolume, streamVolume, 1, 0, speed);
        mSoundPool.play(soundId, streamVolume, streamVolume, 1, 0, speed);
    }
    public void playSound(int soundId,float speed,float soundVolume) {

        mSoundPool.play(soundId, soundVolume, soundVolume, 1, 0, speed);
    }
    /**
     * Stop a Sound
     * @param index - index of the sound to be stopped
     */
    public void stopSound(int index)
    {
        mSoundPool.stop(mSoundPoolMap.get(index));
    }


    /**
     * Deallocates the resources and Instance of SoundManager
     */
    public void cleanup() {
        mSoundPool.release();
        mSoundPool = null;
        mSoundPoolMap.clear();
        mAudioManager.unloadSoundEffects();
        _instance = null;
    }
}