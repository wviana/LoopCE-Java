package br.com.wviana;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Esteganografia {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Referencia para imagem
		BufferedImage img = null;
		
		try {
			//Abre Imagem
			img = ImageIO.read(new File("testImage.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Char limpo para receber mensagem
		byte charMensagem = 0x00;
		
		//Contador de bits lidos 
		int bitCounter = 0;
		
		//Percorre de baixo para cima por causa de como se é armazenado os bytes do .BMP
		for(int i = img.getHeight() -1; i >= 0 ; i--)
		{
			
			//Percorre largura da imagem
			for (int j =0; j < img.getWidth(); j++) {
				
				//Pega cada um dos bytes do valor RGB retornado e desloca bits para direita
				// para se ter valores individuais 
				byte[] colors = {(byte) (img.getRGB(j, i) >> 0 & 0xFF), 
				                 (byte) (img.getRGB(j, i) >> 8 & 0xFF), 
				                 (byte) (img.getRGB(j, i) >> 16 & 0xFF)};
			
				
				for (byte b : colors) {
					
					// Pega somente ultimo bit de cada byte com a mascara 0x01
					b = (byte) (b & 0x01);
					
					// Move o bit para respectivo lugar
					b = (byte) (b << bitCounter);
					
					// Sobe o contador de bit 
					bitCounter++;
					
					// Concatena o bit no byte da mensagem
					charMensagem = (byte) (charMensagem | b);
					
					//Se lido todos os bits para formar 1 byte, imprimir esse
					if(bitCounter > 7)
					{
						
						//Imprimir byte de mensagem
						System.out.printf("%c", charMensagem);					
						
						//Zerar contador de bits e mensagem para não pegar lixo
						bitCounter = 0;
						charMensagem = 0x00;
						
					}
					
				}
				
				
				
				
				
			}
			
		}
		
	

	}

}
