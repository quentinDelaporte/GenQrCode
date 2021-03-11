package genQrCode.GenQrCode;

import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JTextField;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;


import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class QrCode {
	private JLabel lblGnrateurDeQr = new JLabel("    Générateur de QR code");
	private JLabel explication = new JLabel("Les qrCodes sont stocké dans C://Users/<Nom d'utilisateur>/.qrCode");
	private JFrame frame;
	private JTextField textField= new JTextField();
	private static JButton btnGenerer = new JButton("Generer");
	Properties p = System.getProperties();
	String userName = p.getProperty("user.name");
	File dir = new File("c:\\Users\\"+userName+"\\.QrCode");
    File[]  files   =   dir.listFiles();
    int nbQRCode= files.length;

	/**
	 * Launch the application.
	 * @throws WriterException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws WriterException, IOException {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					QrCode window = new QrCode();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	

    private static BitMatrix generateMatrix(String data, int size) throws WriterException {
        BitMatrix bitMatrix = new QRCodeWriter().encode(data, BarcodeFormat.QR_CODE, size, size);
        return bitMatrix;
    }
    
    private static void writeImage(String outputFileName, String imageFormat, BitMatrix bitMatrix) throws IOException  {
        FileOutputStream fileOutputStream = new FileOutputStream(new File(outputFileName));
        MatrixToImageWriter.writeToStream(bitMatrix, imageFormat, fileOutputStream);
        fileOutputStream.close();
    }

	/**
	 * Create the application.
	 */
	public QrCode() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
        if (!dir.exists()) {
            if (dir.mkdir()) {}
        }

		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		btnGenerer.addActionListener(new ButtonGenererListener()); 

		JSplitPane splitPane = new JSplitPane();
		splitPane.setLastDividerLocation(4);
		splitPane.setDividerSize(2);
		frame.getContentPane().add(splitPane, BorderLayout.SOUTH);
		
		splitPane.setRightComponent(btnGenerer);
		
		
		splitPane.setLeftComponent(textField);
		textField.setColumns(10);
		
		frame.getContentPane().add(lblGnrateurDeQr, BorderLayout.NORTH);
	}
	
	  class ButtonGenererListener implements ActionListener{
		    public void actionPerformed(ActionEvent e) {
		    	try {
					QrCodeGen();
				} catch (WriterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		    }

	  }
	  
	  public void QrCodeGen() throws WriterException, IOException {

	        String data = textField.getText();
	        int size = 400;
	        BitMatrix bitMatrix;
			bitMatrix = generateMatrix(data, size);
			nbQRCode++;

			String name= "qrcode" + nbQRCode;
	        String imageFormat = "png";
	        String outputFileName = dir + "\\" + name + "." + imageFormat;

	        // write in a file
	        writeImage(outputFileName, imageFormat, bitMatrix);
	        
	        
	  }
	


	    

}
