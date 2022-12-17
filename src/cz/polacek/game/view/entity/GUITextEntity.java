package cz.polacek.game.view.entity;

public class GUITextEntity {
    char[] text;
    int fontId;
    double positionX;
    double positionY;

    public GUITextEntity(char[] text, int fontId, double positionX, double positionY) {
        this.text = changeCodeToUnderstandThisCrappyFontSpritesheet(text);
        this.fontId = fontId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public GUITextEntity(String text, int fontId, double positionX, double positionY) {
        this.text = changeCodeToUnderstandThisCrappyFontSpritesheet(text.toCharArray());
        this.fontId = fontId;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    private char[] changeCodeToUnderstandThisCrappyFontSpritesheet(char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (chars[i] - 32);
        }
        return chars;
    }

    public char[] getText() {
        return text;
    }

    public void setText(char[] text) {
        this.text = text;
    }

    public double getPositionX() {
        return positionX;
    }

    public void setPositionX(double positionX) {
        this.positionX = positionX;
    }

    public double getPositionY() {
        return positionY;
    }

    public void setPositionY(double positionY) {
        this.positionY = positionY;
    }
}
