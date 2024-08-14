package controle;

import java.awt.*;
import java.text.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.text.MaskFormatter;
import java.sql.*;
import conexao.Conexao;
import javax.swing.table.DefaultTableModel;

public class TabelaCli extends JFrame {
    
    Conexao con_cliente;
    
    JButton bprimei, banterior, bproximo, bultimo;
    JLabel rCodigo, rNome, rEmail, rTel, rData;
    JTextField tCodigo, tNome, tEmail;
    JFormattedTextField tel, data;
    MaskFormatter mTel, mData;
    
    JTable tblClientes;
    JScrollPane scp_tabela;
    
    public TabelaCli() {
        con_cliente = new Conexao(); // inicialização do objeto
        con_cliente.conecta(); // chama o método que conecta
        
        Container tela = getContentPane();
        setLayout(null);

        setTitle("Conexão de Java com MySql");
        setResizable(false);
        
        try {
            mData = new MaskFormatter("##/##/####");
            mTel = new MaskFormatter("(##) ####-####");
            mData.setPlaceholderCharacter('_');
            mTel.setPlaceholderCharacter('_');
        } catch (ParseException excp) {
            excp.printStackTrace();
        }

        rCodigo = new JLabel("Codigo:");
        rCodigo.setBounds(30, 20, 100, 20);
        tCodigo = new JTextField(50);
        tCodigo.setBounds(120, 20, 200, 25);

        rNome = new JLabel("Nome:");
        rNome.setBounds(30, 50, 100, 20);
        tNome = new JTextField(50);
        tNome.setBounds(120, 50, 200, 25);
        
        rData = new JLabel("Data de Nascimento:");
        rData.setBounds(30, 80, 150, 20);
        data = new JFormattedTextField(mData);
        data.setBounds(150, 80, 150, 20);
        
        rTel = new JLabel("Telefone:");
        rTel.setBounds(30, 110, 100, 20);
        tel = new JFormattedTextField(mTel);
        tel.setBounds(120, 110, 150, 20);

        rEmail = new JLabel("Email:");
        rEmail.setBounds(30, 140, 100, 20);
        tEmail = new JTextField(50);
        tEmail.setBounds(120, 140, 200, 25);
        
        bprimei = new JButton("Primeiro");
        bprimei.setBounds(30, 180, 100, 20);
        bprimei.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    con_cliente.resultset.first();
                    mostrar_Dados();
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possível acessar o primeiro registro: " + erro.getMessage());
                }
            }
        });

        banterior = new JButton("Anterior");
        banterior.setBounds(130, 180, 100, 20);
        banterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (con_cliente.resultset.previous()) {
                        mostrar_Dados();
                    }
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possível acessar o registro anterior: " + erro.getMessage());
                }
            }
        });

        bproximo = new JButton("Próximo");
        bproximo.setBounds(230, 180, 100, 20);
        bproximo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (con_cliente.resultset.next()) {
                        mostrar_Dados();
                    }
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possível acessar o próximo registro: " + erro.getMessage());
                }
            }
        });

        bultimo = new JButton("Último");
        bultimo.setBounds(330, 180, 100, 20);
        bultimo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    con_cliente.resultset.last();
                    mostrar_Dados();
                } catch (SQLException erro) {
                    JOptionPane.showMessageDialog(null, "Não foi possível acessar o último registro: " + erro.getMessage());
                }
            }
        });
        
        tela.add(rCodigo);
        tela.add(tCodigo);
        tela.add(rNome);
        tela.add(tNome);
        tela.add(rData);
        tela.add(data);
        tela.add(rTel);
        tela.add(tel);
        tela.add(rEmail);
        tela.add(tEmail);
        tela.add(bprimei);
        tela.add(banterior);
        tela.add(bproximo);
        tela.add(bultimo);
        
        tblClientes = new JTable();
        scp_tabela = new JScrollPane(tblClientes);
        
        scp_tabela.setBounds(50, 210, 550, 200);
        
        tela.add(scp_tabela);
        
        tblClientes.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        tblClientes.setFont(new Font("Arial", Font.BOLD, 12));
        
        tblClientes.setModel(new DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
            },
            new String[] {"Codigo", "Nome", "Data Nascimento", "Telefone", "Email"}
        ) {
            boolean[] canEdit = new boolean[]{false, false, false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        
        tblClientes.setAutoCreateRowSorter(true);
        scp_tabela.setViewportView(tblClientes);
        
        setSize(800, 600);
        setVisible(true);
        setLocationRelativeTo(null);
        
        con_cliente.executaSQL("SELECT * FROM tbclientes ORDER BY cod");
        preencherTabela();
    }
    
    public void preencherTabela() {
        tblClientes.getColumnModel().getColumn(0).setPreferredWidth(4);
        tblClientes.getColumnModel().getColumn(1).setPreferredWidth(150);
        tblClientes.getColumnModel().getColumn(2).setPreferredWidth(11);
        tblClientes.getColumnModel().getColumn(3).setPreferredWidth(14);
        tblClientes.getColumnModel().getColumn(4).setPreferredWidth(100);

        DefaultTableModel modelo = (DefaultTableModel) tblClientes.getModel();
        modelo.setNumRows(0);
        
        try {
            con_cliente.resultset.beforeFirst();
            while (con_cliente.resultset.next()) {
                modelo.addRow(new Object[]{
                    con_cliente.resultset.getString("cod"),
                    con_cliente.resultset.getString("nome"),
                    con_cliente.resultset.getString("dt_nasc"),
                    con_cliente.resultset.getString("telefone"),
                    con_cliente.resultset.getString("email")
                });
            }
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao listar dados da tabela: " + erro.getMessage(), "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public void posicionarRegistro(){ 
        try {
            con_cliente.resultset.first();
            mostrar_Dados();
            
        } catch (SQLException erro) {
JOptionPane.showMessageDialog(null,"Não foi possível posicionar no primeiro registro:"+erro,"Mensagem do Programa",JOptionPane.INFORMATION_MESSAGE);        }
    }
    public void mostrar_Dados() {
        try {
            tCodigo.setText(con_cliente.resultset.getString("cod"));
            tNome.setText(con_cliente.resultset.getString("nome"));
            data.setText(con_cliente.resultset.getString("dt_nasc"));
            tel.setText(con_cliente.resultset.getString("telefone"));
            tEmail.setText(con_cliente.resultset.getString("email"));
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Não localizou dados: " + erro.getMessage(), "Mensagem do Programa", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
