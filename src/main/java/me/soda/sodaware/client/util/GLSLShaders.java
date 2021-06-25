package me.soda.sodaware.client.util;

public enum GLSLShaders {
    REDGLOW("/shaders/redglow.fsh"),
    SNAKE("/shaders/snake.fsh"),
    DJ("/shaders/dj.fsh"),
    WORM("/shaders/worm.fsh"),
    STARS("/shaders/stars.fsh"),
    WEIRDTHING("/shaders/weirdthing.fsh"),
    PARTY("/shaders/party.fsh"),
    CUBE("/shaders/cube.fsh"),
    TRIANGLE("/shaders/triangle.fsh"),
    STORM("/shaders/storm.fsh"),
    BLUEGRID("/shaders/bluegrid.fsh"),
    SKY("/shaders/sky.fsh"),
    PURPLEGRID("/shaders/purplegrid.fsh"),
    SPACE("/shaders/space.fsh"),
    MATRIX("/shaders/matrix.fsh"),
    MATRIXRED("/shaders/matrixred.fsh"),
    MINECRAFT("/shaders/minecraft.fsh"),
    CAVE("/shaders/cave.fsh");

    String s;

    public String get() {
        return this.s;
    }

    private GLSLShaders(String var3) {
        this.s = var3;
    }
}