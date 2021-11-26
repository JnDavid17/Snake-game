
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import org.w3c.dom.events.EventTarget;

public class Snake2 extends JFrame implements ActionListener, KeyListener{
	int cola = 1;
	int colaMaxima;
	int[] colaX = new int [1000];
	int[] colaY = new int [1000];

	int manzanaX;
	int manzanaY;
	

    boolean terminado = false;
    
    boolean listo = false;
    boolean restart = false;
    
    int manzanaDoradaX;
    int manzanaDoradaY;
    
    int contadorTiempo;
    int velocidad = 50;
    
	JLabel  imagenManzana, imagenTrofeo, puntuacionActual, puntuacionMaxima;
	int direccion = 0;  // 0 izq a der, 1 der a izq, 2 arr a aba, 3 aba a arr 

	boolean manzanaDorada = false;
	boolean manzanaDoradaPosicion = false;
	
	boolean manzanaComida = false;
	
	boolean sumarContador = true;
	public static void main(String[] args) {
		Snake2 frame = new Snake2();
	}

	public Snake2(){
		
        Font font2 = new Font("SAN_SERIF", Font.BOLD, 20);
		setBounds(100, 100, 800, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);
		setResizable(false);
		
        setLayout(new BorderLayout());
        setContentPane(new JLabel(new ImageIcon("src/Fondo.png")));
        setLayout(new FlowLayout());
        
		imagenManzana = new JLabel( new ImageIcon("src/manzana.png") );		
		imagenManzana.setBounds( 50, 5, 40, 40);
		add(imagenManzana);
		
		puntuacionActual = new JLabel("0");		
		puntuacionActual.setBounds( 95, 8, 60, 40);
		puntuacionActual.setFont(font2);
		puntuacionActual.setForeground(Color.white);
		add(puntuacionActual); 
		

		
		
		imagenTrofeo = new JLabel( new ImageIcon("src/trofeo.png") );		
		imagenTrofeo.setBounds( 140, 5, 40, 40);
		add(imagenTrofeo);
		
		puntuacionMaxima = new JLabel();		
		puntuacionMaxima.setBounds( 185, 8, 60, 40);
		puntuacionMaxima.setFont(font2);
		puntuacionMaxima.setForeground(Color.white);
		add(puntuacionMaxima);
		this.addKeyListener(this);	
		
        iniciar();
        jugar();
        
	}
	


	public void jugar() {
        while(true) {
        	if (terminado == false) {
        		if (contadorTiempo == 100) {
        			manzanaDoradaPosicion = true;
        			sumarContador = false;
        			manzanaComida = false;
        			manzanaDorada = true;
        			posicionarManzanaDorada();
					contadorTiempo --;
				}else if(contadorTiempo <= 99 && contadorTiempo >0 && sumarContador == false) {
					contadorTiempo--;
				}else {
					sumarContador = true;
					contadorTiempo++;
				}
            	puntuacionActual.setText(String.valueOf(cola));
            	paint(getGraphics());
    			estadoManzana();
    			movimiento();
    			comprobarChoque(getGraphics());
    			repaint();
			}else {
				if (colaMaxima <= cola) {
					colaMaxima = cola;
					puntuacionMaxima.setText(String.valueOf(colaMaxima));
				}else {
					cola = 1;
					esperar(1000);
				}
				
			}
        }
        
	}	
	public void iniciar() {
	       for(int i = 0 ; i < cola ; i++){
	            colaX[i] = 120 - i * 40;
	            colaY[i] = 240;
	        }
	        
	        posicionarManzana();
	        
	}   
    public void posicionarManzanaDorada() {
		int aleatorioX = (int) (Math.random() * (671 - 90)) + 90;
        int aleatorioY = (int) (Math.random() * (571 - 90)) + 90;
		if (manzanaDoradaPosicion == true) {
			manzanaDoradaX = aleatorioX;
			manzanaDoradaY= aleatorioY;
			manzanaDoradaPosicion = false;
		}		
    }
	
	public void posicionarManzana() {
		int aleatorioX = (int) (Math.random() * (671 - 90)) + 90;
        int aleatorioY = (int) (Math.random() * (571 - 90)) + 90;
	    manzanaX = aleatorioX ; 
	    manzanaY = aleatorioY; 	      
	}	
	
	public void paint (Graphics g)
	{
		super.paint(g);	
		if (terminado == false) {
			g.setColor(Color.red);
			g.fillRect(manzanaX, manzanaY, 20, 20);
			if (manzanaDorada == true) {
				g.setColor(Color.yellow);
				g.fillRect(manzanaDoradaX, manzanaDoradaY, 20, 20);
			}
			for(int i = 0; i < cola ; i++){
                if(i == 0){
                	g.setColor(Color.GREEN);
                	g.fillRect(colaX[i], colaY[i], 20, 20);
                }else{
                	g.setColor(Color.blue);
                	g.fillRect(colaX[i], colaY[i], 20, 20);                
                	}
            }
		}else  {
	        Font font1 = new Font("SAN_SERIF", Font.BOLD, 30);
	        g.setColor(Color.blue);
	        g.setFont(font1);
	        g.drawString("Game over", 320, 300);
	        g.setColor(Color.WHITE);
	        g.drawString("Presione R para reintentar", 210, 340);
	        font1 = new Font("SAN_SERIF", Font.BOLD, 20);
	        g.setFont(font1);
	        g.drawString("Velocidades", 540, 50);
	        font1 = new Font("SAN_SERIF", Font.BOLD, 16);
	        g.setFont(font1);
	        g.drawString("Lenta (L)", 480, 75);
	        g.drawString("Normal (N)", 570, 75);
	        g.drawString("Rapida (F)", 670, 75);
	       }

		esperar(velocidad);

			
	}
	
    public static void esperar(int segundos){
        try {
            Thread.sleep(segundos);
         } catch (Exception e) {
            System.out.println(e);
         }
    }   	
	
	public void estadoManzana() {
		
		int auxX = Math.abs(colaX[0] - manzanaX);
		int auxY = Math.abs(colaY[0] - manzanaY);
		
		int auxXDorada = Math.abs(colaX[0] - manzanaDoradaX);
		int auxYDorada = Math.abs(colaY[0] - manzanaDoradaY);

		if ((colaX[0] == manzanaX) && (colaY[0] == manzanaY) || auxX <= 15 && auxY <=15) {
			cola++;
			posicionarManzana();
		}
		
		if ((colaX[0] == manzanaDoradaX) && (colaY[0] == manzanaDoradaY) && manzanaComida == false || auxXDorada <= 15 && auxYDorada <=15 && manzanaComida == false) {
			cola = cola+2;
			manzanaComida = true;
			manzanaDorada = false;
		}
	}
	
	
	public void comprobarChoque(Graphics g) {
        for(int i = cola ; i > 0 ; i--){
            if((colaX[0] == colaX[i]) && (colaY[0] == colaY[i])){
                terminado = true;
            }
        } 
	}
	
	
	public void actionPerformed(ActionEvent e) {	
	}	
	
	public void movimiento() {
		
        for(int i = cola ; i > 0 ; i--){
            colaX[i] = colaX[i - 1]; 
            colaY[i] = colaY[i - 1];
        }

		if( direccion == 0 ){  // izquierda a derecha 
	          colaX[0] = colaX[0]+ 10;
	          if( colaX[0] > 670 ){
	        	  terminado = true;
	          }
			}
			if( direccion == 1 ){  // derecha a izquierda 
		          colaX[0] = colaX[0] - 10;
		          if( colaX[0] < 90 ){
		        	  terminado = true;
		          }
			}
			if( direccion == 2 ){  // arriba a abajo 
		          colaY[0]= colaY[0] + 10;
		          if( colaY[0] > 570 ){
		        	  terminado = true;
		          }
			}
			if( direccion == 3 ){  // abajo a arriba 
		          colaY[0] = colaY[0] - 10;
		          if( colaY[0] < 90 ){
		        	  terminado = true;
		          }
			}
			
	}
	@Override
	public void keyPressed(KeyEvent event) {
		
		//System.out.println(event.getKeyCode());
		comprobarChoque(getGraphics());
		if (event.getKeyCode() == 38) { // Arriba
			if (direccion!=2) {
				direccion = 3;				
			}
		}else if (event.getKeyCode() == 39) { //Derecha
			if (direccion!=1) {
				direccion = 0;
			}
		}else if (event.getKeyCode() == 40) { //Abajo
			if (direccion!=3) {
				direccion = 2;
			}
		}else if (event.getKeyCode() == 37) { //Izquierda
			if (direccion!=0) {
				direccion = 1;				
			}
		}else if(event.getKeyCode() == 82 ) {
			terminado = false;
			direccion = 0;
			cola = 1;
			repaint();
			iniciar();
	}else if(event.getKeyCode() == 82 ) {
		reiniciar();
	}else if(event.getKeyCode() == 76  && terminado == true)  {
		velocidad = 80;
		reiniciar();
	}else if(event.getKeyCode() == 78  && terminado == true)  {
		velocidad = 50;
		reiniciar();
	}else if(event.getKeyCode() == 70  && terminado == true)  {
		velocidad = 20;
		reiniciar();
	}
	
}
	public void reiniciar () {
	terminado = false;
	direccion = 0;
	cola = 1;
	repaint();
	iniciar();
}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void keyTyped(KeyEvent event) {


	}
		
}
