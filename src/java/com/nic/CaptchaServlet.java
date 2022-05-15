package com.nic;
import java.util.*;
import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import java.awt.*;
import java.awt.image.*;

import javax.imageio.*;

import java.awt.geom.*;
public class CaptchaServlet extends HttpServlet  {
	public void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	     
	    HttpSession session = request.getSession(true);
	    response.setContentType("image/jpg");
	    try {
	        String font_file = "Bristles.ttf";
	        font_file = request.getRealPath("/WEB-INF/" + font_file);
	        Font font = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(font_file));
	        font = font.deriveFont(35.0f);
	        Color backgroundColor = Color.WHITE;
	        Color borderColor = Color.BLACK;
	        Color textColor = Color.BLACK;
	        Color circleColor = new Color(160, 160, 160);
	        Font textFont = new Font("Comic Sans Serif", Font.ITALIC, 20);
	        int charsToPrint = 4;
	        int width = request.getParameter("width") != null ? Integer.parseInt(request.getParameter("width")) : 150;
	        int height = request.getParameter("height") != null ? Integer.parseInt(request.getParameter("height")) : 65;
	        int circlesToDraw = 0;
	        float horizMargin = 20.0f;
	        float imageQuality = 0.95f;  
	        double rotationRange = 0.7;  
	        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
	        
	        g.setColor(backgroundColor);
	        g.fillRect(0, 0, width, height);
	        Color[] temp_colors = new Color[12];
	        int index = 0;
	        temp_colors[index++] = Color.BLACK;
	        temp_colors[index++] = Color.BLUE;
	        temp_colors[index++] = Color.RED;
	        temp_colors[index++] = Color.GREEN;
	        temp_colors[index++] = Color.DARK_GRAY;
	        temp_colors[index++] = Color.MAGENTA;
	        
	        g.setColor(circleColor);
	        for (int i = 0; i < circlesToDraw; i++) {
	            int circleRadius = (int) (Math.random() * height / 2.0);
	            int circleX = (int) (Math.random() * width - circleRadius);
	            int circleY = (int) (Math.random() * height - circleRadius);
	            g.drawOval(circleX, circleY, circleRadius * 2, circleRadius * 2);
	        }
	        g.setColor(textColor);
	         
	        g.setFont(font);
	        FontMetrics fontMetrics = g.getFontMetrics();
	        int maxAdvance = fontMetrics.getMaxAdvance();
	        int fontHeight = fontMetrics.getHeight();
	         
	        String elegibleChars = "1234567890";
	        char[] chars = elegibleChars.toCharArray();
	        float spaceForLetters = -horizMargin * 2 + width;
	        float spacePerChar = spaceForLetters / (charsToPrint - 1.0f);
	        AffineTransform transform = g.getTransform();
	        StringBuffer finalString = new StringBuffer();
	        for (int i = 0; i < charsToPrint; i++) {
	            double randomValue = Math.random();
	            int randomIndex = (int) Math.round(randomValue * (chars.length - 1));
	            char characterToShow = chars[randomIndex];
	            finalString.append(characterToShow);
	             
	            int charImageWidth = maxAdvance * 2;
	            int charImageHeight = fontHeight * 2;
	            int charWidth = fontMetrics.charWidth(characterToShow);
	            int charDim = Math.max(maxAdvance, fontHeight);
	            int halfCharDim = (int) (charDim / 2);
	            BufferedImage charImage = new BufferedImage(charDim, charDim, BufferedImage.TYPE_INT_ARGB);
	            Graphics2D charGraphics = charImage.createGraphics();
	            charGraphics.translate(halfCharDim, halfCharDim);
	            double angle = (Math.random() - 0.5) * rotationRange;
	            charGraphics.transform(AffineTransform.getRotateInstance(angle));
	            charGraphics.translate(-halfCharDim, -halfCharDim);
	            int rand_temp = (int) (Math.random() * 6);            
	            textColor = temp_colors[rand_temp];
	            charGraphics.setColor(textColor);
	            charGraphics.setFont(font);
	            int charX = (int) (0.5 * charDim - 0.5 * charWidth);
	            charGraphics.drawString("" + characterToShow, charX,
	                    (int) ((charDim - fontMetrics.getAscent())
	                    / 2 + fontMetrics.getAscent()));
	            float x = horizMargin + spacePerChar * (i) - charDim / 2.0f;
	            int y = (int) ((height - charDim) / 2);
	             g.drawImage(charImage, (int) x, y, charDim, charDim, null, null);
	            charGraphics.dispose();
	        }
	         
	        Iterator iter = ImageIO.getImageWritersByFormatName("JPG");
	        if (iter.hasNext()) {
	            ImageWriter writer = (ImageWriter) iter.next();
	            ImageWriteParam iwp = writer.getDefaultWriteParam();
	            iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	            iwp.setCompressionQuality(imageQuality);
	            writer.setOutput(ImageIO.createImageOutputStream(response.getOutputStream()));
	            IIOImage imageIO = new IIOImage(bufferedImage, null, null);
	            writer.write(null, imageIO, iwp);
	            
                    
                    session.setAttribute("captcha", finalString.toString());
                    session.setMaxInactiveInterval(-1);
                    
                    
		    g.dispose();
	        } else {
	            throw new RuntimeException("no encoder found for jsp");
	        }
	         
	    } catch (IOException ioe) {
	        throw new RuntimeException("Unable to build image", ioe);
	    } catch (FontFormatException e) {
			 
			e.printStackTrace();
		}
}}
