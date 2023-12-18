package com.ku.quizzical.common.util.string;

import com.ku.quizzical.common.helper.ConditionalHelper;
import com.ku.quizzical.common.helper.ExceptionHelper;
import com.ku.quizzical.common.helper.IterationHelper;
import com.ku.quizzical.common.helper.string.CharacterHelper;
import com.ku.quizzical.common.helper.string.StringHelper;
import com.ku.quizzical.common.util.wrapper.IntegerWrapper;
import java.util.Iterator;

/**
 * Utility iterator for Strings
 */
public final class StringIterator implements Iterator<Character> {
    // Instance Fields
    private String text;
    private int index;

    // New Instance Method
    public static StringIterator newInstance(String text) {
        return new StringIterator(text);
    }

    // Constructor Method
    private StringIterator(String text) {
        super();
        this.text = text;
        this.index = 0;
    }

    // Accessor Methods
    public String getText() {
        return this.text;
    }

    public int getIndex() {
        return this.index;
    }

    // Main Instance Methods
    @Override
    public boolean hasNext() {
        return this.index < this.text.length();
    }

    @Override
    public Character next() {
        char next = this.text.charAt(this.index);
        this.index++;
        return next;
    }

    // Operant Methods
    public char peek() {
        return this.text.charAt(this.index);
    }

    public boolean nextStartsWith(String target) {
        return StringHelper.substringEquals(this.text, target, this.index);
    }

    public boolean popIfStartsWithWithPresenceFallthrough(String target) {
        boolean textStartsWith = this.nextStartsWith(target);
        ConditionalHelper.ifThen(textStartsWith, () -> this.popNextCharacters(target));
        return textStartsWith;
    }

    public String popNextWord() {
        StringBuilder nextWord = StringHelper.newBuilder();
        ConditionalHelper.whileLoop(
                () -> this.hasNext() && CharacterHelper.isAlphabetical(this.peek()),
                () -> nextWord.append(this.next()));
        return nextWord.toString();
    }

    public String popWhiteSpace() {
        StringBuilder whiteSpace = StringHelper.newBuilder();
        ConditionalHelper.whileLoop(
                () -> this.hasNext() && CharacterHelper.isWhiteSpace(this.peek()),
                () -> whiteSpace.append(this.next()));
        return whiteSpace.toString();
    }

    public String popNextParentheticToken(char opener) {
        StringBuilder token = StringHelper.newBuilder();
        char closer = CharacterHelper.getClosingParenthesesCharacter(opener);
        IntegerWrapper depth = IntegerWrapper.newInstance(0);
        ConditionalHelper.whileLoop(() -> this.hasNextParentheticCharacter(opener, depth), () -> {
            ConditionalHelper.ifThen(this.peek() == opener, () -> depth.increment());
            ConditionalHelper.ifThen(this.peek() == closer, () -> depth.decrement());
            token.append(this.next());
        });
        return token.toString();
    }

    public void popExpect(String text) {
        ConditionalHelper.ifThen(!this.nextStartsWith(text),
                () -> ExceptionHelper.throwNewIllegalStateException("Unexpected text"));
        this.popNextCharacters(text);
    }

    public void popNextCharacters(String text) {
        this.popNextCharacters(text.length());
    }

    public void popNextCharacters(int length) {
        IterationHelper.forEach(length, () -> this.next());
    }

    // Private Helper Methods
    private boolean hasNextParentheticCharacter(char opener, IntegerWrapper depth) {
        return this.hasNext() && (this.peek() == opener || depth.isGreaterThan(0));
    }
}
