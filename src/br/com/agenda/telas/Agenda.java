package br.com.agenda.telas;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;

//importação do módulo de conexão e do pacote sql
import java.sql.*;
import br.com.agenda.dal.ModuloConexao;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Agenda extends JFrame {
	
	// Criando variáveis de apoio
	Connection con = ModuloConexao.conector(); // Conecta com o banco
	PreparedStatement pst = null; //executa
	ResultSet rs = null;// Resultado do banco
	
	private JPanel contentPane;
	private JTextField txtId;
	private JTextField txtNome;
	private JTextField txtTelefone;
	private JTextField txtEmail;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Agenda frame = new Agenda();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Agenda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Agenda.class.getResource("/br/com/agenda/icones/iconfinder_01-agenda_4620047.png")));
		setResizable(false);
		setTitle("Agenda de contatos");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblStatus = new JLabel("New label");
		lblStatus.setIcon(new ImageIcon(Agenda.class.getResource("/br/com/agenda/icones/dberror.png")));
		lblStatus.setBounds(369, 23, 32, 32);
		contentPane.add(lblStatus);
		
		txtId = new JTextField();
		txtId.setBounds(56, 43, 96, 20);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		JLabel lblId = new JLabel("ID:");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		lblId.setBounds(29, 46, 25, 14);
		contentPane.add(lblId);
		
		txtNome = new JTextField();
		txtNome.setBounds(56, 74, 286, 20);
		contentPane.add(txtNome);
		txtNome.setColumns(10);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNome.setBounds(6, 77, 48, 14);
		contentPane.add(lblNome);
		
		JLabel lblTelefone = new JLabel("Telefone:");
		lblTelefone.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelefone.setBounds(6, 110, 48, 14);
		contentPane.add(lblTelefone);
		
		txtTelefone = new JTextField();
		txtTelefone.setBounds(56, 107, 138, 20);
		contentPane.add(txtTelefone);
		txtTelefone.setColumns(10);
		
		JLabel lblEmail = new JLabel("E-mail:");
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setBounds(6, 141, 48, 14);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(56, 138, 286, 20);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		//ADICIONAR
		JButton btnCreate = new JButton("");
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adicionar();
			}
		});
		btnCreate.setIcon(new ImageIcon(Agenda.class.getResource("/br/com/agenda/icones/create.png")));
		btnCreate.setToolTipText("Adicionar");
		btnCreate.setBounds(56, 169, 64, 64);
		contentPane.add(btnCreate);
		
		JButton btnReade = new JButton("");
		btnReade.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pesquisar();// BOTÃO
			}
		});
		btnReade.setIcon(new ImageIcon(Agenda.class.getResource("/br/com/agenda/icones/read.png")));
		btnReade.setToolTipText("Pesquisar");
		btnReade.setBounds(130, 169, 64, 64);
		contentPane.add(btnReade);
		
		//ALTERAR
		JButton btnUpdate = new JButton("");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				alterar();
			}
		});
		btnUpdate.setIcon(new ImageIcon(Agenda.class.getResource("/br/com/agenda/icones/update.png")));
		btnUpdate.setToolTipText("Atualizar");
		btnUpdate.setBounds(204, 169, 64, 64);
		contentPane.add(btnUpdate);
		
		
		//REMOVER
		JButton btnDelete = new JButton("");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				remover();
			}
		});
		btnDelete.setIcon(new ImageIcon(Agenda.class.getResource("/br/com/agenda/icones/delete.png")));
		btnDelete.setToolTipText("Remover");
		btnDelete.setBounds(278, 169, 64, 64);
		contentPane.add(btnDelete);
		
		//conexão
		con = ModuloConexao.conector();
		if(con != null) {
			//System.out.println("banco conectado");
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/agenda/icones/dbok.png")));
		}else {
			//System.out.println("erro de conexão");
			lblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/agenda/icones/dberror.png")));
		}
	}//Fim do construtor
	
	/** CRUD READ **/
	private void pesquisar() {
		String read = "select * from tb_contatos where id = ?";
		//usamos o try catch para tratar exceções
		try {
			pst = con.prepareStatement(read);
			//passagem do parametro
			pst.setString(1, txtId.getText());
			//atribuimos a variável rs o retorno do comando
			rs = pst.executeQuery(); //executar a query(SQL)
			// usando o retorno para preencher o JFrame
			
			if (rs.next()) {
				txtNome.setText(rs.getString(2));
				txtTelefone.setText(rs.getString(3));
				txtEmail.setText(rs.getString(4));
			} else {
				JOptionPane.showMessageDialog(null, "Contato inexistente");
				limpar(); 
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
	}
	
	/** Limpar campos **/
	private void limpar() {
		txtId.setText(null);
		txtNome.setText(null);
		txtTelefone.setText(null);
		txtEmail.setText(null);
		
	}
	
	
	/** CRUD - Create **/
	//ADICIONAR
	private void adicionar() {
		String create = "insert into tb_contatos (id, nome, fone, email) values(?,?,?,?)";
		try {
			pst = con.prepareStatement(create);
			//passagem de parâmetros
			pst.setString(1, txtId.getText());
			pst.setString(2, txtNome.getText());
			pst.setString(3, txtTelefone.getText());
			pst.setString(4, txtEmail.getText());
			int r = pst.executeUpdate();//EXECUTA E ARMAZENA NA VARIAVEL (R)
			if (r > 0) {
				JOptionPane.showMessageDialog(null, "Contato adicionado com sucesso!");
				limpar();
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível cadastrar!");
				limpar();
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CRUD - Update **/
	//ALTERAR
	private void alterar() {
		String update = "update tb_contatos set nome=?, fone=?, email=? where id=?";
		try {
			pst = con.prepareStatement(update);
			//passagem de parâmetros
			//ATENÇÃO id é o 4° parâmetro
			pst.setString(1, txtNome.getText());
			pst.setString(2, txtTelefone.getText());
			pst.setString(3, txtEmail.getText());
			pst.setString(4, txtId.getText());
			int r = pst.executeUpdate();//EXECUTA E ARMAZENA NA VARIAVEL (R)
			if (r > 0) {
				JOptionPane.showMessageDialog(null, "Contato alterado com sucesso!");
				limpar();
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível alterar!");
				limpar();
			}
			
		}catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/** CRUD - Delete **/
	//DELETAR
	private void remover() {
		//criar uma caixa de diálogo para confirmar a exclusão
		int confirma = JOptionPane.showConfirmDialog(null, "Confirma a exclusão deste contato?", "Atenção", JOptionPane.YES_NO_OPTION);
		if(confirma == JOptionPane.YES_OPTION) {
			String delete = "delete from tb_contatos where id=?";
			try {
				pst = con.prepareStatement(delete);
				pst.setString(1, txtId.getText());
				int r = pst.executeUpdate();
				if (r > 0) {
					limpar();
					JOptionPane.showMessageDialog(null, "Contato removido com sucesso!");					
				} else {
					limpar();
					JOptionPane.showMessageDialog(null, "Impossivel remover o contato!");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	
}//Fim da classe principal
