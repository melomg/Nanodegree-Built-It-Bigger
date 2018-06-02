package com.projects.melih.jokeprovider;

import java.util.concurrent.ThreadLocalRandom;

public class Joker {
    private static final String[] JOKES = new String[]{
            "I asked God fora bike, but I know God doesn't work that way. So I stole a bike and asked for forgiveness.",
            "I want to die peacefully in my sleep, like my grandfather.. Not screaming and yelling like the passengers in his car.",
            "Do not argue with an idiot. He will drag you down to his level and beat you with experience.",
            "The last thing I want to do is hurt you. But it's still on the list.",
            "The early bird might get the worm, but the second mouse gets the cheese.",
            "We never really grow up, we only learn how to act in public.",
            "If 4 out of 5 people SUFFER from diarrhea... does that mean that one enjoys it?",
            "If I agreed with you we'd both be wrong.",
            "A clear conscience is usually the sign of a bad memory.",
            "Never hit a man with glasses. Hit him with a baseball bat.",
            "A bargain is something you don't need at a price you can't resist.",
            "I intend to live forever. So far, so good.",
            "If at first you don't succeed, skydiving is not for you!"
    };

    public String getJoke() {
        final int randomNum = ThreadLocalRandom.current().nextInt(0, JOKES.length);
        return JOKES[randomNum];
    }
}