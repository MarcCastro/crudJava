/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crud4sem;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author Koala Chief
 */
public class Crud4Sem {
    /* variável linha para controlar se o usuário quer adicionar um novo registro
        ou se deseja atualizar um já existente
    */
    public static int linha = -1;
   
    // A variável modelo serve para mostrar através de uma tabela as informações do banco
    public static DefaultTableModel modelo; 
    
    // Método que vincula a JTable com a variável modelo
    public static void criarTabela(JTable tabela){
        modelo = (DefaultTableModel) tabela.getModel();
        
        // Criação das colunas da tabela
        modelo.addColumn("Código");
        modelo.addColumn("Nome");
        modelo.addColumn("Nascimento");
        modelo.addColumn("CPF");
    }    
    
    // Método para ler um ArrayList com os dados do banco e mostrar na aplicação
    public void lerDadosBanco() throws SQLException, ClassNotFoundException{
        
        // Limpa a tabela
        modelo.setNumRows(0);
        
        // Faz a conexão com o banco de dados
        BancoDados banco = new BancoDados();
        
        // Laço para percorrer o ArrayList com as informações do banco
        for (Cliente a : banco.dadosBanco()){
            
            // Adiciona as informações na tabela que vai aparecer na aplicação
            modelo.addRow(new Object[]{
                a.getCod(),
                a.getNome(),
                a.getNasc(),
                a.getCpf()
            });
        }
    }
    
    // Método para ler um Arraylist com os dados coletados através da pesquisa pelo nome e então mostrar na aplicação
    public void lerDadosPesquisa(String desc) throws SQLException, ClassNotFoundException{
        
        // Limpa a tabela
        modelo.setNumRows(0);
        
        // Faz a conexão com o banco de dados
        BancoDados banco = new BancoDados();
        
        // Laço para percorrer o ArrayList com as informações do banco coletados através da pesquisa
        for (Cliente a : banco.pesquisaPorDesc(desc)){
            
            // Adiciona as informações na tabela que vai aparecer na aplicação
            modelo.addRow(new Object[]{
                a.getCod(),
                a.getNome(),
                a.getNasc(),
                a.getCpf()
            });
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, SQLException, ClassNotFoundException {
        // TODO code application logic here
        
        // Faz a conexão com o banco de dados
        BancoDados novoBD = new BancoDados();
        
        // Método que já faz a leitura de todas as informações do banco
        novoBD.dadosBanco();
        
        /* Criação de uma instância para usar os métodos readJTable(),
            readJTableForDesc() ....
        */
        Crud4Sem instCrud = new Crud4Sem();
        
        // Criação de todos os elementos do frame
        JFrame crud = new JFrame("CRUD");
        crud.setBounds(100, 100, 400, 500);
        crud.getContentPane().setLayout(null);
        crud.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel labCodigo = new JLabel("Codigo: ");
        labCodigo.setBounds(20, 55, 75, 25);
        crud.add(labCodigo);
        JTextField tfCodigo = new JTextField();
        tfCodigo.setForeground(Color.BLACK);
        tfCodigo.setBounds(95, 55, 100, 25);
        tfCodigo.setEditable(false);
        crud.add(tfCodigo);
        
        JLabel labNome = new JLabel("Nome: ");
        labNome.setBounds(20, 90, 75, 25);
        crud.add(labNome);
        JTextField tfNome = new JTextField();
        tfNome.setForeground(Color.BLACK);
        tfNome.setBounds(95, 90, 100, 25);
        crud.add(tfNome);
        
        JLabel labNascimento = new JLabel("Nascimento: ");
        labNascimento.setBounds(20, 125, 75, 25);
        crud.add(labNascimento);
        JTextField tfNascimento = new JFormattedTextField(new MaskFormatter("##/##/####"));
        tfNascimento.setForeground(Color.BLACK);
        tfNascimento.setBounds(95, 125, 100, 25);
        crud.add(tfNascimento);
        
        JLabel labCpf = new JLabel("CPF: ");
        labCpf.setBounds(20, 160, 75, 25);
        crud.add(labCpf);
        JTextField tfCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
        tfCpf.setForeground(Color.BLACK);
        tfCpf.setBounds(95, 160, 100, 25);
        crud.add(tfCpf);
        
        JTable tabela = new JTable();
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollTabela = new JScrollPane(tabela);
        scrollTabela.setBounds(0, 195, 380, 200);
        crud.add(scrollTabela);
        
        crud.setVisible(true);
        
        // Criação da tabela que será exibida na aplicação
        criarTabela(tabela);
        
        // Método para ler um ArrayList com os dados do banco e mostrar na aplicação
        instCrud.lerDadosBanco();
        
        JButton btnNovo = new JButton("Novo");
        btnNovo.setBounds(20, 20, 75, 25);
        btnNovo.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        // Limpeza de todos os text field da aplicação
                        tfCodigo.setText("");
                        tfNome.setText("");
                        tfNascimento.setText("");
                        tfCpf.setText("");
                        
                        // Muda o valor da variável linha 
                        linha = -1;
                        try {
                            // Método para ler um ArrayList com os dados do banco e mostrar na aplicação
                            instCrud.lerDadosBanco();
                        } catch (SQLException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );
        crud.add(btnNovo);
        
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setBounds(105, 20, 75, 25);
        btnSalvar.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        try {
                            if (linha < 0){
                                // Inserção de dados no banco
                                novoBD.inserirDados(tfNome.getText(), tfNascimento.getText(), tfCpf.getText());
                            } else {
                                // Atualização de dados no banco
                                novoBD.atualizarDados(Integer.parseInt(tfCodigo.getText()), tfNome.getText(), tfNascimento.getText(), tfCpf.getText());
                            }
                            // Método para ler um ArrayList com os dados do banco e mostrar na aplicação
                            instCrud.lerDadosBanco();
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );
        crud.add(btnSalvar);
        
        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(190, 20, 75, 25);
        btnExcluir.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        try {
                            // Método para excluir um registro do banco a partir do código passado como parâmetro
                            novoBD.excluirDados(Integer.parseInt(tfCodigo.getText()));
                            
                            // Método para ler um ArrayList com os dados do banco e mostrar na aplicação
                            instCrud.lerDadosBanco();
                        } catch (SQLException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );
        crud.add(btnExcluir);
        
        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.setBounds(275, 20, 95, 25);
        btnPesquisar.addActionListener(
                new ActionListener(){
                    @Override
                    public void actionPerformed(ActionEvent e){
                        /* Criação de variável para armazenar o nome do cliente
                            pesquisado
                        */ 
                        String nome = tfNome.getText();
                        
                        try {
                            // Método para ler um Arraylist com os dados coletados através da pesquisa pelo nome e então mostrar na aplicação
                            instCrud.lerDadosPesquisa(nome);
                            
                        } catch (SQLException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (ClassNotFoundException ex) {
                            Logger.getLogger(Crud4Sem.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
        );
        crud.add(btnPesquisar);
        
        /* Mouse Listener que serve para pegar a interação do usuário com determinada
            linha da tabela e retorna os valores nos text field
        */
        tabela.addMouseListener(new MouseAdapter(){
            public void mouseReleased(MouseEvent e){
                linha = tabela.getSelectedRow();
                tfCodigo.setText((String)modelo.getValueAt(linha, 0));
                tfNome.setText((String)modelo.getValueAt(linha, 1));
                tfNascimento.setText((String)modelo.getValueAt(linha, 2));
                tfCpf.setText((String)modelo.getValueAt(linha, 3));
            }
        });
    
    }
    
}
