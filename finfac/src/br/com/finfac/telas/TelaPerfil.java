/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package br.com.finfac.telas;

import br.com.finfac.dao.ModuloConexao;
import static br.com.finfac.telas.TelaEvento.cboEventValidado;
import static br.com.finfac.telas.TelaGastos.cboSituacao;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import net.proteanit.sql.DbUtils;

/**
 *
 * @author João Sales
 */
public class TelaPerfil extends javax.swing.JInternalFrame {

    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;

    /**
     * Creates new form TelaPerfil
     */
    public TelaPerfil() {
        initComponents();
        getContentPane().setBackground(Color.WHITE);
        conexao = ModuloConexao.conectar();
        pesquisar_gastoevento();
    }

    private void pesquisar_gastoevento() {
        String sql = "SELECT Gastos.valor, Gastos.descricao, Gastos.meiopagamento, Gastos.situacao, Evento.descricao, Evento.dataevento, Evento.localevento "
                + "FROM tbgastosevento AS Gastos "
                + "INNER JOIN tbeventos AS Evento ON (Gastos.idevento = Evento.idevento) "
                + "WHERE Gastos.valor LIKE ? "
                + "ORDER BY CASE Gastos.situacao "
                + "           WHEN 'Em observação' THEN 1 "
                + "           WHEN 'Recusado' THEN 2 "
                + "           WHEN 'Aprovado' THEN 3 "
                + "         END";

        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, "%" + txtPesquisar.getText() + "%");
            rs = pst.executeQuery();
            tblGastos.setModel(DbUtils.resultSetToTableModel(rs));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    private void setar_campos() {
        int setar = tblGastos.getSelectedRow();
        txtValor.setText(tblGastos.getModel().getValueAt(setar, 0).toString());
        txtDescricao.setText(tblGastos.getModel().getValueAt(setar, 1).toString());
        cboMeio.setSelectedItem(tblGastos.getValueAt(setar, 2));
        cboSituacao.setSelectedItem(tblGastos.getValueAt(setar, 3));
        txtData.setText(tblGastos.getModel().getValueAt(setar, 5).toString());
        txtHorario.setText(tblGastos.getModel().getValueAt(setar, 6).toString());

        btnEditar.setEnabled(true);
        btnDeletar.setEnabled(true);
    }

    private void limpar() {
        cboMeio.setSelectedItem(" ");
        cboSituacao.setSelectedItem(0);
        txtData.setText(null);
        txtHorario.setText(null);
        txtDescricao.setText(null);
        txtValor.setText(null);
        ((DefaultTableModel) tblGastos.getModel()).setRowCount(0);
        tblGastos.setEnabled(true);
        btnEditar.setEnabled(false);
        btnDeletar.setEnabled(false);
    }

    private void alterar() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma as alterações nos gastos deste evento ?", "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "update tbgastosevento set datagasto=?,horagasto=?,descricao=?,valor=?,meiopagamento=?,situacao=? where descricao=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtData.getText());
                pst.setString(2, txtHorario.getText());
                pst.setString(3, txtDescricao.getText());
                pst.setString(4, txtValor.getText());
                pst.setString(5, cboMeio.getSelectedItem().toString());
                pst.setString(6, cboSituacao.getSelectedItem().toString());
                pst.setString(7, txtDescricao.getText());
                if ((txtValor.getText().isEmpty()) || txtDescricao.getText().isEmpty() || txtData.getText().isEmpty() || txtHorario.getText().isEmpty() || cboMeio.getSelectedItem().equals(" ") || cboSituacao.getSelectedItem().equals(" ")) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatórios");
                } else {
                    int adicionado = pst.executeUpdate();
                    if (adicionado > 0) {
                        JOptionPane.showMessageDialog(null, "Gasto atualizado com sucesso");
                        limpar();
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    private void excluir() {
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse gasto ?", "Atenção", JOptionPane.YES_NO_CANCEL_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "delete from tbgastosevento where descricao=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtDescricao.getText());
                int apagado = pst.executeUpdate();
                if (apagado > 0) {
                    JOptionPane.showMessageDialog(null, "Gasto excluido com sucesso");
                    limpar();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblGastos = new javax.swing.JTable();
        txtGastos = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        btnEditar = new javax.swing.JButton();
        cboMeio = new javax.swing.JComboBox<>();
        txtValor = new javax.swing.JTextField();
        cboSituacao = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        txtData = new javax.swing.JTextField();
        txtHorario = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnDeletar = new javax.swing.JButton();
        txtPesquisar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();

        setIconifiable(true);
        setMaximizable(true);
        setTitle("Tela de Administrador");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblGastos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblGastos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGastosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGastos);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 60, 1000, 200));

        txtGastos.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtGastosKeyReleased(evt);
            }
        });
        getContentPane().add(txtGastos, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, 70, -1));

        jLabel2.setText("Local");
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 370, -1, -1));

        jLabel3.setText("ID");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 30, -1, -1));

        jLabel4.setText("Meio de Pagamento");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 330, -1, -1));

        jLabel6.setText("Descrição");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 280, -1, -1));

        jLabel7.setText("Data");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 280, -1, 20));

        btnEditar.setBackground(new java.awt.Color(78, 92, 188));
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/editar.png"))); // NOI18N
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        getContentPane().add(btnEditar, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 430, -1, -1));

        cboMeio.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Dinheiro", "Cartão de crédito", "Cartão de débito", "Transferência bancária", "Boleto bancário", "Cheque", "Pix", "PayPal", "PicPay", "Mercado Pago", "Outro " }));
        cboMeio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMeioActionPerformed(evt);
            }
        });
        getContentPane().add(cboMeio, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 330, -1, -1));

        txtValor.setText("0.0");
        getContentPane().add(txtValor, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 280, 100, -1));

        cboSituacao.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Em observação", "Aprovado", "Recusado" }));
        cboSituacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboSituacaoActionPerformed(evt);
            }
        });
        getContentPane().add(cboSituacao, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 370, 150, -1));

        txtDescricao.setColumns(20);
        txtDescricao.setRows(5);
        jScrollPane3.setViewportView(txtDescricao);

        getContentPane().add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 430, -1));
        getContentPane().add(txtData, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 280, 110, -1));
        getContentPane().add(txtHorario, new org.netbeans.lib.awtextra.AbsoluteConstraints(904, 370, 120, -1));

        jLabel8.setText("Situação");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, -1));

        jLabel9.setText("Valor");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, -1, -1));

        btnDeletar.setBackground(new java.awt.Color(78, 92, 188));
        btnDeletar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/br/com/finfac/icones/trash.png"))); // NOI18N
        btnDeletar.setEnabled(false);
        btnDeletar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarActionPerformed(evt);
            }
        });
        getContentPane().add(btnDeletar, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 430, -1, -1));

        txtPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPesquisarKeyReleased(evt);
            }
        });
        getContentPane().add(txtPesquisar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, 170, -1));

        jLabel5.setText("Pesquisar gasto por valor");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 30, -1, -1));

        setBounds(0, 0, 1074, 560);
    }// </editor-fold>//GEN-END:initComponents

    private void txtGastosKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtGastosKeyReleased
        pesquisar_gastoevento();
    }//GEN-LAST:event_txtGastosKeyReleased

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        alterar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void cboMeioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMeioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboMeioActionPerformed

    private void cboSituacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboSituacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboSituacaoActionPerformed

    private void btnDeletarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarActionPerformed
        excluir();
    }//GEN-LAST:event_btnDeletarActionPerformed

    private void txtPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPesquisarKeyReleased
        // TODO add your handling code here:
        pesquisar_gastoevento();
    }//GEN-LAST:event_txtPesquisarKeyReleased

    private void tblGastosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGastosMouseClicked
        setar_campos();
    }//GEN-LAST:event_tblGastosMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDeletar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JComboBox<String> cboMeio;
    public static javax.swing.JComboBox<String> cboSituacao;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable tblGastos;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtGastos;
    private javax.swing.JTextField txtHorario;
    private javax.swing.JTextField txtPesquisar;
    private javax.swing.JTextField txtValor;
    // End of variables declaration//GEN-END:variables
}
