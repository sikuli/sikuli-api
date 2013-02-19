package org.sikuli.api.robot;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * Simple JFrame so that we have something to copy text from in the test
 *
 */
@SuppressWarnings("serial")
public class CopySource extends JFrame {

	private JPanel contentPane;
	private JTextField textField1;
	private JLabel lblField_1;
	private JTextField textField2;
	private JLabel lblField_2;
	private JTextField textField3;

	/**
	 * Create the frame.
	 */
	public CopySource() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		{
			JLabel lblField = new JLabel("Field1");
			contentPane.add(lblField);
		}
		{
			textField1 = new JTextField();
			textField1.setText("hello");
			contentPane.add(textField1);
			textField1.setColumns(10);
		}
		{
			lblField_1 = new JLabel("Field2");
			contentPane.add(lblField_1);
		}
		{
			textField2 = new JTextField();
			textField2.setText("world");
			contentPane.add(textField2);
			textField2.setColumns(10);
		}
		{
			lblField_2 = new JLabel("Field3");
			contentPane.add(lblField_2);
		}
		{
			textField3 = new JTextField();
			textField3.setText("test ü");
			contentPane.add(textField3);
			textField3.setColumns(10);
		}
	}

}
