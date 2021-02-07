package de.ft.ekes.BackEnd.virtualtileworld;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WorldGenerator {
    private BufferedImage image;
    private int imageWidth=0;
    private int imageHeight=0;
    public WorldGenerator(String pathtoimage, int width, int height){
        this.imageWidth=width;
        this.imageHeight=height;
        readImage(pathtoimage);
    }


    public void readImage(String s) {


        BufferedImage ergebnis = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB); // bzw. TYPE_INT_RGB falls du kein Alphakanal brauchst
        try{
            ergebnis.getGraphics().drawImage(ImageIO.read(new File(s)), 0,0, imageWidth, imageHeight, null);
        }catch(IOException e){
            //Use fall back image
            try {
                ergebnis.getGraphics().drawImage(ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("Land.jpg")), 0,0, imageWidth, imageHeight, null);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

        }

        image=ergebnis;
    }
    public void generateWorld(VirtualTileWorld vtw){
        luminanz();
        for(int x=0;x<imageWidth;x++){
            for(int y=0;y<imageHeight;y++){
            if(getRed(x,y)>200){
                vtw.getTiles().get(x).get(imageHeight-y-1).setLandType(LandType.LAND);
            }else{
                vtw.getTiles().get(x).get(imageHeight-y-1).setLandType(LandType.WATER);
            }
            }
        }
    }



    public void luminanz()
    {
        for(int x=0;x<image.getWidth();x++){
            for(int y=0;y<image.getHeight();y++){
                int r=getRed(x,y);
                int g=getGreen(x,y);
                int b=getBlue(x,y);


                int luminanz=(int) (0.229*r+0.587*g+0.114*b);
                setpixelcolor(x,y,luminanz,luminanz,luminanz);
            }
        }
    }
    public void setpixelcolor(int x, int y,int r,int g,int b){

        int rgb = r;
        rgb = (rgb << 8) + g;
        rgb = (rgb << 8) + b;

        image.setRGB(x,y,rgb);
    }


    public int getRed(int x, int y){

        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color c = new Color(red,green,blue,1);

        return red;
    }

    public int getGreen(int x, int y){

        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color c = new Color(red,green,blue,1);

        return green;
    }

    public int getBlue(int x, int y){

        int rgb = image.getRGB(x, y);
        int red = (rgb >> 16) & 0xFF;
        int green = (rgb >> 8) & 0xFF;
        int blue = rgb & 0xFF;
        Color c = new Color(red,green,blue,1);

        return blue;
    }
}
