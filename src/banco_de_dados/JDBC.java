package banco_de_dados;

import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

public class JDBC extends JFrame {
	private JPanel pnPrincipal, pnTable;
	private JLabel dolphin, label1, label2, label3, label4;
	private ImageIcon img;
	private JButton btProx, btAnt, btPrimeiro, btUltimo, btSair, btadm, btfiltrar;
	private JTextField tfCodigo, tfProduto, tfQtd, tfPreco, tfadm;
	private JRadioButton rbCodigo, rbProduto, rbQtd, rbPreco;
	private ButtonGroup filtragem;
	private JTable table;
	private JScrollPane scrollTable;
	private BD bd;
	private PreparedStatement st;
	private ResultSet resultSet;
	static ImageIcon icon = new ImageIcon("C:\\\\Users\\\\Raio1\\\\Desktop\\\\CEAP\\\\Aulas Java\\\\banco_de_dados\\\\src\\\\Imagem\\\\dolphin.png");

	public static void main(String args[]) {
		JFrame frame = new JDBC();
		frame.setIconImage(icon.getImage());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public JDBC() {
		inicializarComponentes();
		definirEventos();
	}

	public void inicializarComponentes() {

		img = new ImageIcon("C:\\Users\\Raio1\\Desktop\\CEAP\\Aulas Java\\banco_de_dados\\src\\Imagem\\mysql.png");

		pnPrincipal = new JPanel();
		pnPrincipal.setLayout(null);

		dolphin = new JLabel(img);
		label1 = new JLabel("Código:");
		label2 = new JLabel("Produto:");
		label3 = new JLabel("Qtd:");
		label4 = new JLabel("Preço:");
		add(label1);
		add(label2);
		add(label3);
		add(label4);
		add(dolphin);
		dolphin.setBounds(400, 120, 218, 218);
		label1.setBounds(10, 10, 80, 20);
		label2.setBounds(10, 30, 100, 20);
		label3.setBounds(10, 50, 50, 20);
		label4.setBounds(10, 70, 80, 20);

		tfadm = new JTextField(60);
		tfCodigo = new JTextField(20);
		tfProduto = new JTextField(20);
		tfQtd = new JTextField(20);
		tfPreco = new JTextField(20);
		pnPrincipal.add(tfadm);
		pnPrincipal.add(tfCodigo);
		pnPrincipal.add(tfProduto);
		pnPrincipal.add(tfQtd);
		pnPrincipal.add(tfPreco);
		tfadm.setBounds(350, 35, 250, 20);
		tfCodigo.setBounds(55, 10, 30, 20);
		tfProduto.setBounds(60, 30, 80, 20);
		tfQtd.setBounds(35, 50, 40, 20);
		tfPreco.setBounds(50, 70, 70, 20);

		rbCodigo = new JRadioButton("Codigo");
		rbProduto = new JRadioButton("Produto");
		rbQtd = new JRadioButton("Qtd");
		rbPreco = new JRadioButton("Preco");
		filtragem = new ButtonGroup();
		filtragem.add(rbCodigo);
		filtragem.add(rbPreco);
		filtragem.add(rbProduto);
		filtragem.add(rbQtd);

		pnPrincipal.add(rbCodigo);
		pnPrincipal.add(rbProduto);
		pnPrincipal.add(rbQtd);
		pnPrincipal.add(rbPreco);
		rbCodigo.setBounds(180, 10, 80, 20);
		rbProduto.setBounds(180, 30, 80, 20);
		rbQtd.setBounds(180, 50, 80, 20);
		rbPreco.setBounds(180, 70, 80, 20);

		btfiltrar = new JButton("Filtrar");
		btadm = new JButton("Executar");
		btProx = new JButton("Próximo");
		btAnt = new JButton("Anterior");
		btPrimeiro = new JButton("Primeiro");
		btUltimo = new JButton("Ultimo");
		btSair = new JButton("Sair");
		btfiltrar.setBackground(new Color(30, 144, 255));
		btadm.setBackground(new Color(30, 144, 255));
		btAnt.setBackground(new Color(70, 130, 180));
		btPrimeiro.setBackground(new Color(70, 130, 180));
		btProx.setBackground(new Color(70, 130, 180));
		btUltimo.setBackground(new Color(70, 130, 180));
		btSair.setBackground(new Color(70, 130, 180));

		pnPrincipal.add(btfiltrar);
		pnPrincipal.add(btadm);
		pnPrincipal.add(btProx);
		pnPrincipal.add(btAnt);
		pnPrincipal.add(btPrimeiro);
		pnPrincipal.add(btUltimo);
		pnPrincipal.add(btSair);
		btfiltrar.setBounds(260, 70, 90, 20);
		btadm.setBounds(460, 100, 90, 20);
		btProx.setBounds(110, 410, 90, 20);
		btAnt.setBounds(10, 410, 90, 20);
		btPrimeiro.setBounds(210, 410, 90, 20);
		btUltimo.setBounds(310, 410, 90, 20);
		btSair.setBounds(520, 410, 90, 20);

		scrollTable = new JScrollPane();
		add(scrollTable);
		scrollTable.setBounds(50, 100, 500, 300);

		setTitle("Banco de Dados Do Supermarket!");
		setBounds(0, 0, 640, 480);
		setResizable(false);

		pnTable = new JPanel(new BorderLayout());
		pnTable.setBorder(new TitledBorder("Estoque"));
		pnTable.add(scrollTable);
		pnTable.setBounds(20, 100, 380, 300);
		pnTable.setBackground(new Color(30, 144, 255));

		
		pnPrincipal.add(pnTable);
		add(pnPrincipal);

		bd = new BD();
		if (!bd.getConnection()) {
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}
		carregarTabela();
		atualizarCampos();
	}

	public void definirEventos() {
		btadm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tfadm.getText().equals("")) {
					tfadm.requestFocus();
					return;
				}
				try{
					if(!bd.getConnection()){
						JOptionPane.showMessageDialog(null,"Falha na conexão!");
						System.exit(0);
					}
					st = bd.c.prepareStatement(tfadm.getText());
					resultSet = st.executeQuery();
					DefaultTableModel tableModel = new DefaultTableModel(
							new String[]{}, 0) {
					};
					int qtdeColunas = resultSet.getMetaData().getColumnCount();
					for(int indice = 1; indice <= qtdeColunas; indice++){
						tableModel.addColumn(resultSet.getMetaData().getColumnName(indice));
						
					}
					table = new JTable(tableModel);
					DefaultTableModel dtm = (DefaultTableModel) table.getModel();
					
					while(resultSet.next()){
						try{
							String[] dados = new String[qtdeColunas];
							for(int i = 1; i<=qtdeColunas; i++){
								dados[i-1] = resultSet.getString(i);
								System.out.println(resultSet.getString(i));
							}
							dtm.addRow(dados);
							System.out.println();
						}catch (SQLException erro){
							
						}
						scrollTable.setViewportView(table);
					}
					resultSet.close();
					st.close();
					bd.close();
				}catch (Exception erro){
					JOptionPane.showMessageDialog(null,"Comando Inválido"+erro.toString());
				}
			}
		});
		btfiltrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbCodigo.isSelected()) {
					String sql = "select codigo from estoque";
					try {
						st = bd.c.prepareStatement(sql);
						resultSet = st.executeQuery();
						DefaultTableModel tableModel = new DefaultTableModel(new String[] {}, 0) {
						};
						int qtdeColunas = resultSet.getMetaData().getColumnCount();
						for (int indice = 1; indice <= qtdeColunas; indice++) {
							tableModel.addColumn(resultSet.getMetaData().getColumnName(indice));

						}
						table = new JTable(tableModel);
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();

						while (resultSet.next()) {
							try {
								String[] dados = new String[qtdeColunas];
								for (int i = 1; i <= qtdeColunas; i++) {
									dados[i - 1] = resultSet.getString(i);
									System.out.println(resultSet.getString(i));
								}
								dtm.addRow(dados);
								System.out.println();
							} catch (SQLException erro) {

							}
							scrollTable.setViewportView(table);
						}
					} catch (SQLException erro) {
						JOptionPane.showMessageDialog(null, "Erro! " + erro.toString());
					}
				}
				if (rbPreco.isSelected()) {
					String sql = "select preco from estoque";
					try {
						st = bd.c.prepareStatement(sql);
						resultSet = st.executeQuery();
						DefaultTableModel tableModel = new DefaultTableModel(new String[] {}, 0) {
						};
						int qtdeColunas = resultSet.getMetaData().getColumnCount();
						for (int indice = 1; indice <= qtdeColunas; indice++) {
							tableModel.addColumn(resultSet.getMetaData().getColumnName(indice));

						}
						table = new JTable(tableModel);
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();

						while (resultSet.next()) {
							try {
								String[] dados = new String[qtdeColunas];
								for (int i = 1; i <= qtdeColunas; i++) {
									dados[i - 1] = resultSet.getString(i);
									System.out.println(resultSet.getString(i));
								}
								dtm.addRow(dados);
								System.out.println();
							} catch (SQLException erro) {

							}
							scrollTable.setViewportView(table);
						}
					} catch (SQLException erro) {
						JOptionPane.showMessageDialog(null, "Erro! " + erro.toString());
					}
				}
				if (rbProduto.isSelected()) {
					String sql = "select produto from estoque";
					try {
						st = bd.c.prepareStatement(sql);
						resultSet = st.executeQuery();
						DefaultTableModel tableModel = new DefaultTableModel(new String[] {}, 0) {
						};
						int qtdeColunas = resultSet.getMetaData().getColumnCount();
						for (int indice = 1; indice <= qtdeColunas; indice++) {
							tableModel.addColumn(resultSet.getMetaData().getColumnName(indice));

						}
						table = new JTable(tableModel);
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();

						while (resultSet.next()) {
							try {
								String[] dados = new String[qtdeColunas];
								for (int i = 1; i <= qtdeColunas; i++) {
									dados[i - 1] = resultSet.getString(i);
									System.out.println(resultSet.getString(i));
								}
								dtm.addRow(dados);
								System.out.println();
							} catch (SQLException erro) {

							}
							scrollTable.setViewportView(table);
						}
					} catch (SQLException erro) {
						JOptionPane.showMessageDialog(null, "Erro! " + erro.toString());
					}
				}
				if (rbQtd.isSelected()) {
					String sql = "select quantidade from estoque";
					try {
						st = bd.c.prepareStatement(sql);
						resultSet = st.executeQuery();
						DefaultTableModel tableModel = new DefaultTableModel(new String[] {}, 0) {
						};
						int qtdeColunas = resultSet.getMetaData().getColumnCount();
						for (int indice = 1; indice <= qtdeColunas; indice++) {
							tableModel.addColumn(resultSet.getMetaData().getColumnName(indice));

						}
						table = new JTable(tableModel);
						DefaultTableModel dtm = (DefaultTableModel) table.getModel();

						while (resultSet.next()) {
							try {
								String[] dados = new String[qtdeColunas];
								for (int i = 1; i <= qtdeColunas; i++) {
									dados[i - 1] = resultSet.getString(i);
									System.out.println(resultSet.getString(i));
								}
								dtm.addRow(dados);
								System.out.println();
							} catch (SQLException erro) {

							}
							scrollTable.setViewportView(table);
						}
					} catch (SQLException erro) {
						JOptionPane.showMessageDialog(null, "Erro! " + erro.toString());
					}
				}
			}
		});
		btProx.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultSet.next();
					atualizarCampos();
				} catch (SQLException erro) {
				}
			}
		});

		btAnt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultSet.previous();
					atualizarCampos();
				} catch (SQLException erro) {
				}
			}
		});

		btPrimeiro.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultSet.first();
					atualizarCampos();
				} catch (SQLException erro) {
				}
			}
		});

		btUltimo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultSet.last();
					atualizarCampos();
				} catch (SQLException erro) {
				}
			}
		});

		btSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					resultSet.close();
					st.close();
				} catch (SQLException erro) {
				}
				bd.close();
				System.exit(0);
			}
		});
	}

	public void carregarTabela() {
		String sql = "select * from estoque";
		try {
			st = bd.c.prepareStatement(sql);
			resultSet = st.executeQuery();
			DefaultTableModel tableModel = new DefaultTableModel(new String[] {}, 0) {
			};
			int qtdeColunas = resultSet.getMetaData().getColumnCount();
			for (int indice = 1; indice <= qtdeColunas; indice++) {
				tableModel.addColumn(resultSet.getMetaData().getColumnName(indice));

			}
			table = new JTable(tableModel);
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();

			while (resultSet.next()) {
				try {
					String[] dados = new String[qtdeColunas];
					for (int i = 1; i <= qtdeColunas; i++) {
						dados[i - 1] = resultSet.getString(i);
						System.out.println(resultSet.getString(i));
					}
					dtm.addRow(dados);
					System.out.println();
				} catch (SQLException erro) {

				}
				scrollTable.setViewportView(table);
			}
		} catch (SQLException erro) {
			JOptionPane.showMessageDialog(null, "Erro! " + erro.toString());
		}
	}

	public void atualizarCampos() {
		try {
			if (resultSet.isAfterLast()) {
				resultSet.last();
			}
			if (resultSet.isBeforeFirst()) {
				resultSet.first();
			}
			tfCodigo.setText(resultSet.getString("codigo"));
			tfProduto.setText(resultSet.getString("produto"));
			tfQtd.setText(resultSet.getString("quantidade"));
			tfPreco.setText(resultSet.getString("preco"));
		} catch (SQLException erro) {

		}

	}
}