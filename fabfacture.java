/*
  MIT License

	Copyright (c) 2022 MontreNous

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
 */
package fabfacture;

import com.mxrck.autocompleter.AutoCompleterCallback;
import com.mxrck.autocompleter.TextAutoCompleter;
import static fabfacture.PGConnexion.ConnxPG;
import static fabfacture.PGConnexion.c;
import static fabfacture.PGConnexion.pst;
import static fabfacture.PGConnexion.reslt;
import static fabfacture.PGConnexion.stmt;
import static fabfacture.PGConnexion.stmt1;
import java.awt.HeadlessException;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.String.format;
import java.net.URL;
import java.sql.SQLException;
import java.text.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
/**
 *
 * @author Sbenhadja
 */
public class FabFramFatcture extends javax.swing.JFrame{

    public static DefaultTableModel dt;
    public static DefaultTableModel dtJ;
    public static DefaultTableModel dtD;
    public static java.util.Date date;
    public static JTextField txtPathReport = new JTextField();

    public static TextAutoCompleter complete;
    
    public static  AutocompleteField fieldNam;
    public static  AutocompleteField fieldNamJ;
    public static final List<String> values = new ArrayList();
    public static final Function<String, List<String>> lookup = text -> values.stream ()
                                .filter ( v -> !text.isEmpty () && v.toLowerCase ().contains ( text.toLowerCase () ) && !v.equals ( text ) )
                                .collect ( Collectors.toList () );
    
    public static  AutocompleteField fieldDiot;
    public static  AutocompleteField fieldDiotJ;
    public static final List<String> valuesD = new ArrayList();
    public static final Function<String, List<String>> lookupD = text -> valuesD.stream ()
                                .filter ( v -> !text.isEmpty () && v.toLowerCase ().contains ( text.toLowerCase () ) && !v.equals ( text ) )
                                .collect ( Collectors.toList () );
    
    public FabFramFatcture() {
        FabFramFatcture.i = 0;
        FabFramFatcture.MontantRedu = 0;
        FabFramFatcture.reduction = 0;
        FabFramFatcture.totalTVA = 0;
        FabFramFatcture.totalHT = 0;
        initComponents();
        AutoCompleteDecorator.decorate(jComboClient);
        MetreFieldAutoCont();
        ConnxPG();
        LastNumFacture();
        OsType();
        FiltoJComboWilaya(jComboBoxDestination, "Destination");
        FiltoJCombo(jComboboxProduit, "Produit acheter");
        FiltoJCombo(jComboboxProduit1, "Produit acheter");
        FiltoJCombo(jComboboxProduit2, "Produit");
        TableModel();
        TableModelJ();
        TableModelD();
        
        
        jInternalFrameJournal.setVisible(false);

    }

    private void MetreFieldAutoCont(){     
        fieldNam = new AutocompleteField ( lookup );
        fieldNam.setColumns ( 15 );
        fieldNam.setToolTipText("Nom et prenom"); // NOI18N
        fieldNam.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldNamAutoMouseClicked(evt,fieldNam);
            }
        });  
        jPanel4.add(fieldNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 70, 330, 30));
            
        
        fieldDiot = new AutocompleteField ( lookupD );
        fieldDiot.setColumns ( 15 );
        fieldDiot.setToolTipText("Diot"); // NOI18N
        fieldDiot.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldNamAutoMouseClicked(evt,fieldDiot);
            }
        });  
        jPanel4.add(fieldDiot, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 110, 330, 30));
        
        
        
        
        
        fieldNamJ = new AutocompleteField ( lookup );
        fieldNamJ.setColumns ( 15 );
        fieldNamJ.setToolTipText("Nom et prenom"); // NOI18N
        fieldNamJ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldNamJAutoMouseClicked(evt,fieldNamJ);
            }
        });  
        jPanel7.add(fieldNamJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 230, 30));
        
        fieldDiotJ = new AutocompleteField ( lookupD );
        fieldDiotJ.setColumns ( 15 );
        fieldDiotJ.setToolTipText("Diot"); // NOI18N
        fieldDiotJ.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextFieldNamJAutoMouseClicked(evt,fieldDiotJ);
            }
        });  
        jPanel7.add(fieldDiotJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 230, 30));
                    
    }
    
    public static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat formatter1 = new SimpleDateFormat("ddMM");
    SimpleDateFormat formatter2 = new SimpleDateFormat("yyMMdd");
    SimpleDateFormat formatter3 = new SimpleDateFormat("yy");

    
    
    public static java.sql.Date sqdat() {
        
        date = jDateChooser1.getDate();
        if (date == null) {
            jDateChooser1.setDate(new Date());
            date = new Date();
            JOptionPane.showMessageDialog(null, date);
            return null;
        } else {
            String dat = formatter.format(date);
            java.sql.Date sqdat = java.sql.Date.valueOf(dat);
            return sqdat;
        }
    }

    public static java.sql.Date sqdatDebut() {
        date = jDateChooserDeb.getDate();
        if (date == null) {
            JOptionPane.showMessageDialog(null, "Date debut non valide");
            return null;
        } else {
            String dat = formatter.format(date);
            java.sql.Date sqdatDeb = java.sql.Date.valueOf(dat);
            return sqdatDeb;
        }

    }

    public static java.sql.Date sqdatFin() {
        date = jDateChooserFin.getDate();
        if (date == null) {
            JOptionPane.showMessageDialog(null, "Date fin non valide");
            return null;
        } else {
            String dat = formatter.format(date);
            java.sql.Date sqdatfin = java.sql.Date.valueOf(dat);
            return sqdatfin;
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

        jMenuItem3 = new javax.swing.JMenuItem();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDetail = new javax.swing.JTable();
        jDialog2 = new javax.swing.JDialog();
        jLabel26 = new javax.swing.JLabel();
        jTextFieldNomFichier = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jTextFieldChemin = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jButton6 = new javax.swing.JButton();
        jDialog3 = new javax.swing.JDialog();
        jButtonAjtWilaya = new javax.swing.JButton();
        jTextFieldWilaya = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jComboBoxWilaya = new javax.swing.JComboBox<>();
        jButtonSupWilaya = new javax.swing.JButton();
        jLabel30 = new javax.swing.JLabel();
        buttonGroup1 = new javax.swing.ButtonGroup();
        menuC = new javax.swing.JPopupMenu();
        panelC = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jInternalFrameFactur = new javax.swing.JInternalFrame();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jTextFieldReduction = new javax.swing.JTextField();
        jComboboxProduit = new javax.swing.JComboBox();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        jTextFieldNumFct = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jTextFieldQtt = new javax.swing.JTextField();
        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxModeP = new javax.swing.JComboBox();
        jComboBoxUM = new javax.swing.JComboBox();
        jLabelDel = new javax.swing.JLabel();
        jComboBoxTVA = new javax.swing.JComboBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtableFactur = new javax.swing.JTable();
        jComboBoxDestination = new javax.swing.JComboBox();
        jInternalFrameClient = new javax.swing.JInternalFrame();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jTFdiot = new javax.swing.JTextField();
        jTFnom = new javax.swing.JTextField();
        jTFactivite = new javax.swing.JTextField();
        jTFadress = new javax.swing.JTextField();
        jTFnif = new javax.swing.JTextField();
        jTFai = new javax.swing.JTextField();
        jTFrc = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jComboClient = new javax.swing.JComboBox<>();
        jLabel31 = new javax.swing.JLabel();
        jTFdateAbn = new javax.swing.JTextField();
        jInternalFrameCalculatrice = new javax.swing.JInternalFrame();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jComboboxProduit1 = new javax.swing.JComboBox();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jInternalFrameProduit = new javax.swing.JInternalFrame();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton11 = new javax.swing.JButton();
        jComboboxProduit2 = new javax.swing.JComboBox();
        jInternalFrameJournal = new javax.swing.JInternalFrame();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jDateChooserFin = new com.toedter.calendar.JDateChooser();
        jDateChooserDeb = new com.toedter.calendar.JDateChooser();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        jtableJournal = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jLabel23 = new javax.swing.JLabel();
        jLabelRemarque = new javax.swing.JLabel();
        jLabel1Excel = new javax.swing.JLabel();
        jLabelRapport = new javax.swing.JLabel();
        jInternalFrameUtilisateur = new javax.swing.JInternalFrame();
        jBtnAjoutUtili = new javax.swing.JButton();
        jBtnSuprtUtili = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jTFUtilisateur = new javax.swing.JTextField();
        jTFPass = new javax.swing.JTextField();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel24 = new javax.swing.JLabel();
        jComboBoxUtilisateur = new javax.swing.JComboBox<>();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuClient = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItemProduit = new javax.swing.JMenuItem();
        jMenuWilaya = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItemFacture = new javax.swing.JMenuItem();
        jMenuItemJournal = new javax.swing.JMenuItem();
        jMenuItemCalcul = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuUtilisateur = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuUnlockPrec = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuUnlockNum = new javax.swing.JMenu();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        jMenuItemRemise0 = new javax.swing.JMenuItem();
        jMenuRepport = new javax.swing.JMenu();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuTheme = new javax.swing.JMenu();
        jMenu6 = new javax.swing.JMenu();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem13 = new javax.swing.JMenuItem();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jRadioButtonMenuItem1 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem2 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem3 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem4 = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonMenuItem5 = new javax.swing.JRadioButtonMenuItem();
        jMenu3 = new javax.swing.JMenu();

        jMenuItem3.setText("jMenuItem3");

        jMenuItem4.setText("jMenuItem4");
        jPopupMenu1.add(jMenuItem4);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jDialog1.setAlwaysOnTop(true);
        jDialog1.setResizable(false);

        jTableDetail.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTableDetail.setRowHeight(24);
        jScrollPane2.setViewportView(jTableDetail);

        jDialog1.getContentPane().add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jDialog2.setResizable(false);
        jDialog2.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jProgressBar1.setBackground(new java.awt.Color(255, 255, 255));
        jProgressBar1.setForeground(new java.awt.Color(102, 102, 255));
        jDialog2.getContentPane().add(jProgressBar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 610, 10));
        jDialog2.getContentPane().add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 138, 20));

        jTextFieldNomFichier.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jDialog2.getContentPane().add(jTextFieldNomFichier, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 348, 28));

        jButton5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton5.setText("Parcourir");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jDialog2.getContentPane().add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 10, -1, -1));

        jTextFieldChemin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jDialog2.getContentPane().add(jTextFieldChemin, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 10, 348, 28));

        jLabel27.setBackground(new java.awt.Color(255, 255, 255));
        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel27.setText("Chemin du repertoire");
        jDialog2.getContentPane().add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel28.setText("Nom de fichier ");
        jDialog2.getContentPane().add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        jButton6.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jButton6.setText("Converter");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jDialog2.getContentPane().add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 120, 107, -1));

        jDialog3.setAlwaysOnTop(true);
        jDialog3.setMinimumSize(new java.awt.Dimension(384, 189));
        jDialog3.setResizable(false);

        jButtonAjtWilaya.setText("Ajouter");
        jButtonAjtWilaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAjtWilayaActionPerformed(evt);
            }
        });

        jLabel29.setText("Wilaya de:");

        jComboBoxWilaya.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButtonSupWilaya.setText("Supprimer");
        jButtonSupWilaya.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSupWilayaActionPerformed(evt);
            }
        });

        jLabel30.setForeground(new java.awt.Color(0, 102, 0));

        javax.swing.GroupLayout jDialog3Layout = new javax.swing.GroupLayout(jDialog3.getContentPane());
        jDialog3.getContentPane().setLayout(jDialog3Layout);
        jDialog3Layout.setHorizontalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldWilaya, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jComboBoxWilaya, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jDialog3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel30, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jDialog3Layout.createSequentialGroup()
                                .addComponent(jButtonSupWilaya)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButtonAjtWilaya, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(48, Short.MAX_VALUE))
        );
        jDialog3Layout.setVerticalGroup(
            jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldWilaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addComponent(jComboBoxWilaya, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jDialog3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSupWilaya)
                    .addComponent(jButtonAjtWilaya))
                .addGap(1, 1, 1)
                .addComponent(jLabel30)
                .addContainerGap(48, Short.MAX_VALUE))
        );

        menuC.setFocusable(false);
        menuC.setMaximumSize(new java.awt.Dimension(300, 100));
        menuC.setMinimumSize(new java.awt.Dimension(26, 20));

        jScrollPane4.setViewportView(jList1);

        javax.swing.GroupLayout panelCLayout = new javax.swing.GroupLayout(panelC);
        panelC.setLayout(panelCLayout);
        panelCLayout.setHorizontalGroup(
            panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 333, Short.MAX_VALUE)
            .addGroup(panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelCLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        panelCLayout.setVerticalGroup(
            panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 384, Short.MAX_VALUE)
            .addGroup(panelCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(panelCLayout.createSequentialGroup()
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Systeme de facturation FAB");
        setBackground(new java.awt.Color(255, 255, 255));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(new ImageIcon(this.getClass().getResource("fab.png")).getImage());
        setMinimumSize(new java.awt.Dimension(1230, 550));
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                formComponentResized(evt);
            }
        });
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(null);

        jInternalFrameFactur.setMaximizable(true);
        jInternalFrameFactur.setTitle("Facturation");
        jInternalFrameFactur.setAutoscrolls(true);
        jInternalFrameFactur.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/fab.png"))); // NOI18N
        jInternalFrameFactur.setMinimumSize(new java.awt.Dimension(780, 470));
        jInternalFrameFactur.setName(""); // NOI18N
        jInternalFrameFactur.setPreferredSize(new java.awt.Dimension(780, 470));
        jInternalFrameFactur.setVisible(true);
        jInternalFrameFactur.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jInternalFrameFacturComponentResized(evt);
            }
        });

        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel3.setPreferredSize(new java.awt.Dimension(780, 470));

        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextFieldReduction.setEditable(false);
        jTextFieldReduction.setPreferredSize(new java.awt.Dimension(6, 30));
        jTextFieldReduction.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextFieldReductionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextFieldReductionMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextFieldReductionMousePressed(evt);
            }
        });
        jTextFieldReduction.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldReductionActionPerformed(evt);
            }
        });
        jTextFieldReduction.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldReductionKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldReduction, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 150, 130, -1));

        jComboboxProduit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Produit acheter" }));
        jComboboxProduit.setToolTipText("");
        jComboboxProduit.setPreferredSize(new java.awt.Dimension(138, 29));
        jComboboxProduit.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboboxProduitItemStateChanged(evt);
            }
        });
        jComboboxProduit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                seClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComboboxProduitMouseEntered(evt);
            }
        });
        jComboboxProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboboxProduitActionPerformed(evt);
            }
        });
        jPanel4.add(jComboboxProduit, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 230, -1));

        jButton3.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton3.setText("Ajouter au facture");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 230, 190, -1));

        jButton4.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jButton4.setText("Facturer");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 380, 190, -1));

        jLabel2.setText("Recherche par DIOT:");
        jPanel4.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, -1, 30));

        jLabel3.setText("Recherche par nom et prenom:");
        jPanel4.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 70, -1, 30));

        jButton7.setText("Initialiser");
        jButton7.setEnabled(false);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 0, 110, -1));

        jTextFieldNumFct.setEditable(false);
        jTextFieldNumFct.setText("Num Facture");
        jTextFieldNumFct.setPreferredSize(new java.awt.Dimension(96, 27));
        jTextFieldNumFct.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldNumFctKeyReleased(evt);
            }
        });
        jPanel4.add(jTextFieldNumFct, new org.netbeans.lib.awtextra.AbsoluteConstraints(41, 0, 150, -1));

        jButton8.setText("-");
        jButton8.setEnabled(false);
        jButton8.setPreferredSize(new java.awt.Dimension(39, 27));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 40, 27));

        jButton9.setText("+");
        jButton9.setPreferredSize(new java.awt.Dimension(45, 27));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(192, 0, -1, 27));

        jTextFieldQtt.setEnabled(false);
        jTextFieldQtt.setPreferredSize(new java.awt.Dimension(6, 30));
        jTextFieldQtt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextFieldQttMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jTextFieldQttMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextFieldQttMousePressed(evt);
            }
        });
        jTextFieldQtt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldQttKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldQttKeyTyped(evt);
            }
        });
        jPanel4.add(jTextFieldQtt, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 150, 130, -1));

        jDateChooser1.setDate(new Date());
        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jPanel4.add(jDateChooser1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 350, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/logofab.png"))); // NOI18N
        jPanel4.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, -10, 220, 80));

        jComboBoxModeP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mode de paiement", "Espece", "Chec", "A terme" }));
        jComboBoxModeP.setMinimumSize(new java.awt.Dimension(161, 29));
        jComboBoxModeP.setPreferredSize(new java.awt.Dimension(161, 29));
        jPanel4.add(jComboBoxModeP, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 190, 230, -1));

        jComboBoxUM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "U/M", "QUINTAL", "SAC" }));
        jComboBoxUM.setPreferredSize(new java.awt.Dimension(100, 29));
        jPanel4.add(jComboBoxUM, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 150, 110, -1));

        jLabelDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/Sans.png"))); // NOI18N
        jLabelDel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelDelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelDelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelDelMouseExited(evt);
            }
        });
        jLabelDel.setVisible(false);
        jPanel4.add(jLabelDel, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 290, -1, 30));

        jComboBoxTVA.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "TVA", "0", "2", "5", "7", "17", "19" }));
        jComboBoxTVA.setPreferredSize(new java.awt.Dimension(62, 29));
        jPanel4.add(jComboBoxTVA, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 150, -1, -1));

        jtableFactur.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID_facture", "Num_facture", "Date_facture", "TVA", "Quantétés"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtableFactur.setRowHeight(24);
        jtableFactur.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableFacturMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jtableFactur);

        jPanel4.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 650, 120));

        jComboBoxDestination.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Destination" }));
        jComboBoxDestination.setPreferredSize(new java.awt.Dimension(100, 29));
        jComboBoxDestination.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxDestinationActionPerformed(evt);
            }
        });
        jPanel4.add(jComboBoxDestination, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 190, 200, -1));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(22, Short.MAX_VALUE)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jInternalFrameFacturLayout = new javax.swing.GroupLayout(jInternalFrameFactur.getContentPane());
        jInternalFrameFactur.getContentPane().setLayout(jInternalFrameFacturLayout);
        jInternalFrameFacturLayout.setHorizontalGroup(
            jInternalFrameFacturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameFacturLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrameFacturLayout.setVerticalGroup(
            jInternalFrameFacturLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameFacturLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        getContentPane().add(jInternalFrameFactur);
        jInternalFrameFactur.setBounds(10, 10, 790, 500);

        jInternalFrameClient.setResizable(true);
        jInternalFrameClient.setTitle("Client");
        jInternalFrameClient.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/fab.png"))); // NOI18N
        jInternalFrameClient.setMinimumSize(new java.awt.Dimension(380, 470));
        jInternalFrameClient.setNormalBounds(new java.awt.Rectangle(20, 490, 380, 470));
        jInternalFrameClient.setPreferredSize(new java.awt.Dimension(380, 470));
        jInternalFrameClient.setVisible(true);

        jButton15.setText("Ajouter");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton16.setText("Modifier");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });

        jLabel10.setText("Diot:");

        jLabel11.setText("Nom complet:");

        jLabel12.setText("Activité:");

        jLabel13.setText("Adresse:");

        jLabel14.setText("NIF:");

        jLabel15.setText("AI:");

        jLabel16.setText("RC/Carte F:");

        jTFdiot.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFdiot.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFdiot.setPreferredSize(new java.awt.Dimension(6, 28));
        jTFdiot.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFdiotKeyReleased(evt);
            }
        });

        jTFnom.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFnom.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFnom.setPreferredSize(new java.awt.Dimension(6, 28));
        jTFnom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFnomKeyReleased(evt);
            }
        });

        jTFactivite.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFactivite.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFactivite.setPreferredSize(new java.awt.Dimension(6, 28));

        jTFadress.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFadress.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFadress.setPreferredSize(new java.awt.Dimension(6, 28));

        jTFnif.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFnif.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFnif.setPreferredSize(new java.awt.Dimension(6, 28));

        jTFai.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFai.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFai.setPreferredSize(new java.awt.Dimension(6, 28));

        jTFrc.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFrc.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFrc.setPreferredSize(new java.awt.Dimension(6, 28));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel25.setText("Gestion des clients");

        jComboClient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Listes des clients" }));
        jComboClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboClientActionPerformed(evt);
            }
        });

        jLabel31.setText("Date Abnm:");

        jTFdateAbn.setEditable(false);
        jTFdateAbn.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFdateAbn.setText("Aujourd'huit");
        jTFdateAbn.setMinimumSize(new java.awt.Dimension(6, 28));
        jTFdateAbn.setPreferredSize(new java.awt.Dimension(6, 28));

        javax.swing.GroupLayout jInternalFrameClientLayout = new javax.swing.GroupLayout(jInternalFrameClient.getContentPane());
        jInternalFrameClient.getContentPane().setLayout(jInternalFrameClientLayout);
        jInternalFrameClientLayout.setHorizontalGroup(
            jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameClientLayout.createSequentialGroup()
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jInternalFrameClientLayout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton15)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jInternalFrameClientLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addComponent(jLabel25)
                        .addGap(0, 93, Short.MAX_VALUE))
                    .addGroup(jInternalFrameClientLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboClient, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jInternalFrameClientLayout.createSequentialGroup()
                                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel31, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrameClientLayout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTFai, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFdiot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFnom, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFactivite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFadress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFnif, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrameClientLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTFrc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrameClientLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTFdateAbn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        jInternalFrameClientLayout.setVerticalGroup(
            jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrameClientLayout.createSequentialGroup()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFdiot, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFnom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFactivite, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFadress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFnif, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFrc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(jTFdateAbn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jInternalFrameClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton15)
                    .addComponent(jButton16))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(jInternalFrameClient);
        jInternalFrameClient.setBounds(30, 520, 380, 470);

        jInternalFrameCalculatrice.setTitle("Calculatrice de reduction %");
        jInternalFrameCalculatrice.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/fab.png"))); // NOI18N
        jInternalFrameCalculatrice.setMaximumSize(new java.awt.Dimension(67, 45));
        jInternalFrameCalculatrice.setMinimumSize(new java.awt.Dimension(67, 45));
        jInternalFrameCalculatrice.setPreferredSize(new java.awt.Dimension(380, 470));
        jInternalFrameCalculatrice.setVisible(true);
        jInternalFrameCalculatrice.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                jInternalFrameCalculatriceComponentMoved(evt);
            }
        });
        jInternalFrameCalculatrice.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jInternalFrameCalculatricePropertyChange(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().setLayout(null);

        jTextField14.setEditable(false);
        jTextField14.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField14.setText("Prix Unitaire");
        jTextField14.setToolTipText("Prix Unitaire");
        jTextField14.setMinimumSize(new java.awt.Dimension(330, 28));
        jTextField14.setPreferredSize(new java.awt.Dimension(330, 28));
        jInternalFrameCalculatrice.getContentPane().add(jTextField14);
        jTextField14.setBounds(20, 90, 330, 28);

        jTextField15.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField15.setText("Qtt achetée");
        jTextField15.setToolTipText("Qtt achetée");
        jTextField15.setMinimumSize(new java.awt.Dimension(330, 28));
        jTextField15.setPreferredSize(new java.awt.Dimension(330, 28));
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField15MousePressed(evt);
            }
        });
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jTextField15.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField15KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField15KeyTyped(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jTextField15);
        jTextField15.setBounds(20, 40, 330, 28);

        jTextField16.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField16.setText("Prix de reduction");
        jTextField16.setToolTipText("Prix de reduction");
        jTextField16.setMinimumSize(new java.awt.Dimension(330, 28));
        jTextField16.setPreferredSize(new java.awt.Dimension(330, 28));
        jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jTextField16MouseEntered(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField16MousePressed(evt);
            }
        });
        jTextField16.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField16KeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField16KeyTyped(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jTextField16);
        jTextField16.setBounds(20, 180, 330, 28);

        jTextField17.setEditable(false);
        jTextField17.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField17.setText("Pourcentage de reduction total");
        jTextField17.setToolTipText("Pourcentage de reduction total");
        jTextField17.setMinimumSize(new java.awt.Dimension(330, 28));
        jTextField17.setPreferredSize(new java.awt.Dimension(330, 28));
        jTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField17MousePressed(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jTextField17);
        jTextField17.setBounds(20, 280, 330, 28);

        jComboboxProduit1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboboxProduit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Produit acheter" }));
        jComboboxProduit1.setPreferredSize(new java.awt.Dimension(122, 27));
        jComboboxProduit1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboboxProduit1ItemStateChanged(evt);
            }
        });
        jComboboxProduit1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboboxProduit1ActionPerformed(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jComboboxProduit1);
        jComboboxProduit1.setBounds(20, 10, 330, 27);

        jTextField18.setEditable(false);
        jTextField18.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField18.setText("Montant sans reduction");
        jTextField18.setToolTipText("Montant sans reduction");
        jTextField18.setMinimumSize(new java.awt.Dimension(330, 28));
        jTextField18.setPreferredSize(new java.awt.Dimension(330, 28));
        jInternalFrameCalculatrice.getContentPane().add(jTextField18);
        jTextField18.setBounds(20, 120, 330, 28);

        jTextField19.setEditable(false);
        jTextField19.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField19.setText("Prix après la reduction");
        jTextField19.setToolTipText("Prix après la reduction");
        jTextField19.setMinimumSize(new java.awt.Dimension(330, 28));
        jTextField19.setPreferredSize(new java.awt.Dimension(330, 28));
        jTextField19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTextField19MousePressed(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jTextField19);
        jTextField19.setBounds(20, 250, 330, 28);

        jButton17.setText("Ajouter");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jButton17);
        jButton17.setBounds(230, 340, 85, 29);

        jButton18.setText("Annuler l'ajoute");
        jButton18.setEnabled(false);
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        jInternalFrameCalculatrice.getContentPane().add(jButton18);
        jButton18.setBounds(40, 340, 160, 29);

        getContentPane().add(jInternalFrameCalculatrice);
        jInternalFrameCalculatrice.setBounds(430, 520, 380, 470);

        jInternalFrameProduit.setClosable(true);
        jInternalFrameProduit.setTitle("Produit");
        jInternalFrameProduit.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jInternalFrameProduit.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/fab.png"))); // NOI18N
        jInternalFrameProduit.setMinimumSize(new java.awt.Dimension(380, 470));
        jInternalFrameProduit.setName(""); // NOI18N
        jInternalFrameProduit.setNormalBounds(new java.awt.Rectangle(430, 490, 380, 470));
        jInternalFrameProduit.setPreferredSize(new java.awt.Dimension(380, 470));
        jInternalFrameProduit.setVisible(true);
        jInternalFrameProduit.getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setText("ID Produit:");

        jLabel5.setText("Designation:");

        jLabel6.setText("TVA:");

        jTextField1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField1.setMinimumSize(new java.awt.Dimension(6, 30));
        jTextField1.setPreferredSize(new java.awt.Dimension(6, 29));
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField1KeyReleased(evt);
            }
        });

        jTextField2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField2.setMinimumSize(new java.awt.Dimension(6, 30));
        jTextField2.setPreferredSize(new java.awt.Dimension(6, 29));
        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField3.setMinimumSize(new java.awt.Dimension(6, 30));
        jTextField3.setPreferredSize(new java.awt.Dimension(6, 29));

        jButton1.setText("Ajouter");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Modifier");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jButton2)
                        .addGap(89, 89, 89)
                        .addComponent(jButton1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(51, 51, 51)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)))
        );

        jTabbedPane4.addTab("Produit", jPanel1);

        jLabel7.setText("ID Produit:");

        jLabel8.setText("Valeur de prix:");

        jTextField5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTextField5.setPreferredSize(new java.awt.Dimension(6, 28));
        jTextField5.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextField5KeyTyped(evt);
            }
        });

        jButton11.setText("Sauvgarder");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jComboboxProduit2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jComboboxProduit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Produit" }));
        jComboboxProduit2.setToolTipText("");
        jComboboxProduit2.setPreferredSize(new java.awt.Dimension(82, 27));
        jComboboxProduit2.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboboxProduit2ItemStateChanged(evt);
            }
        });
        jComboboxProduit2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jComboboxProduit2MouseEntered(evt);
            }
        });
        jComboboxProduit2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboboxProduit2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboboxProduit2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(41, 41, 41))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(jComboboxProduit2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addComponent(jButton11)
                .addGap(178, 178, 178))
        );

        jTabbedPane4.addTab("   Prix    ", jPanel2);

        jInternalFrameProduit.getContentPane().add(jTabbedPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 360, 410));

        getContentPane().add(jInternalFrameProduit);
        jInternalFrameProduit.setBounds(830, 520, 380, 470);

        jInternalFrameJournal.setMaximizable(true);
        jInternalFrameJournal.setResizable(true);
        jInternalFrameJournal.setTitle("Journal");
        jInternalFrameJournal.setAutoscrolls(true);
        jInternalFrameJournal.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/fab.png"))); // NOI18N
        jInternalFrameJournal.setMinimumSize(new java.awt.Dimension(780, 470));
        jInternalFrameJournal.setName(""); // NOI18N
        jInternalFrameJournal.setPreferredSize(new java.awt.Dimension(780, 470));
        jInternalFrameJournal.setVisible(true);
        jInternalFrameJournal.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jInternalFrameJournalComponentResized(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(255, 255, 204));
        jPanel6.setPreferredSize(new java.awt.Dimension(780, 470));

        jPanel7.setBackground(new java.awt.Color(255, 255, 204));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDateChooser1.setDate(new Date());
        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooserFin.setToolTipText("Date fin");
        jDateChooserFin.setDate(new Date());
        jPanel7.add(jDateChooserFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, 220, 30));

        jDateChooser1.setDate(new Date());
        jDateChooser1.setDateFormatString("dd/MM/yyyy");
        jDateChooserDeb.setToolTipText("Date début");
        jDateChooserDeb.setDate(new Date());
        jDateChooserDeb.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jDateChooserDebMousePressed(evt);
            }
        });
        jPanel7.add(jDateChooserDeb, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 30, 220, 30));

        jLabel21.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel21.setText("Filtrage par Nom ou Diot:");
        jPanel7.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 0, 190, 30));

        jLabel22.setFont(new java.awt.Font("Segoe UI Semilight", 1, 12)); // NOI18N
        jLabel22.setText("Jusqu'au");
        jPanel7.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 60, 60, 20));

        jtableJournal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Client", "Num_facture", "Date_facture", "Quantétés", "Montant TTC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Float.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtableJournal.setRowHeight(24);
        jtableJournal.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jtableJournalMouseClicked(evt);
            }
        });
        jScrollPane6.setViewportView(jtableJournal);

        jPanel7.add(jScrollPane6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 680, 260));

        jButton12.setText("Journal");
        jButton12.setPreferredSize(new java.awt.Dimension(97, 28));
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel7.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 80, 100, 30));

        jLabel23.setFont(new java.awt.Font("Segoe UI Light", 1, 14)); // NOI18N
        jLabel23.setText("Journal par période:");
        jPanel7.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 150, 30));

        jLabelRemarque.setForeground(new java.awt.Color(255, 0, 0));
        jPanel7.add(jLabelRemarque, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 530, 30));

        jLabel1Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/excel.png"))); // NOI18N
        jLabel1Excel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel1ExcelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel1ExcelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel1ExcelMouseExited(evt);
            }
        });
        jPanel7.add(jLabel1Excel, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 380, -1, 40));

        jLabelRapport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/rapport.png"))); // NOI18N
        jLabelRapport.setEnabled(false);
        jLabelRapport.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabelRapportMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabelRapportMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabelRapportMouseExited(evt);
            }
        });
        jPanel7.add(jLabelRapport, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 380, -1, 40));

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jInternalFrameJournalLayout = new javax.swing.GroupLayout(jInternalFrameJournal.getContentPane());
        jInternalFrameJournal.getContentPane().setLayout(jInternalFrameJournalLayout);
        jInternalFrameJournalLayout.setHorizontalGroup(
            jInternalFrameJournalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameJournalLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 745, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jInternalFrameJournalLayout.setVerticalGroup(
            jInternalFrameJournalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameJournalLayout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        getContentPane().add(jInternalFrameJournal);
        jInternalFrameJournal.setBounds(830, 10, 790, 500);

        jInternalFrameUtilisateur.setTitle("Utilisateur");
        jInternalFrameUtilisateur.setFrameIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/fab.png"))); // NOI18N
        jInternalFrameUtilisateur.setMinimumSize(new java.awt.Dimension(380, 470));
        jInternalFrameUtilisateur.setNormalBounds(new java.awt.Rectangle(20, 490, 380, 470));
        jInternalFrameUtilisateur.setPreferredSize(new java.awt.Dimension(380, 470));
        jInternalFrameUtilisateur.setVisible(true);

        jBtnAjoutUtili.setText("Ajouter");
        jBtnAjoutUtili.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnAjoutUtiliActionPerformed(evt);
            }
        });

        jBtnSuprtUtili.setText("Supprimer");
        jBtnSuprtUtili.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBtnSuprtUtiliActionPerformed(evt);
            }
        });

        jLabel18.setText("Utilisateur:");

        jLabel19.setText("Mot de passe:");

        jLabel20.setText("Autorisation:");

        jTFUtilisateur.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFUtilisateur.setPreferredSize(new java.awt.Dimension(6, 30));
        jTFUtilisateur.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFUtilisateurKeyReleased(evt);
            }
        });

        jTFPass.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jTFPass.setPreferredSize(new java.awt.Dimension(6, 30));
        jTFPass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTFPassKeyReleased(evt);
            }
        });

        jCheckBox1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Journal");
        jCheckBox1.setEnabled(false);

        jCheckBox2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox2.setText("Facturation");

        jCheckBox3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jCheckBox3.setText("Client, Produit et Utilisateur");

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel24.setText("Gestion des administrateur");

        jComboBoxUtilisateur.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Utilisateur" }));
        jComboBoxUtilisateur.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBoxUtilisateurItemStateChanged(evt);
            }
        });
        jComboBoxUtilisateur.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxUtilisateurActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrameUtilisateurLayout = new javax.swing.GroupLayout(jInternalFrameUtilisateur.getContentPane());
        jInternalFrameUtilisateur.getContentPane().setLayout(jInternalFrameUtilisateurLayout);
        jInternalFrameUtilisateurLayout.setHorizontalGroup(
            jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jInternalFrameUtilisateurLayout.createSequentialGroup()
                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jInternalFrameUtilisateurLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jComboBoxUtilisateur, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jInternalFrameUtilisateurLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jInternalFrameUtilisateurLayout.createSequentialGroup()
                                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel19)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel18))
                                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jInternalFrameUtilisateurLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jTFUtilisateur, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jTFPass, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addGroup(jInternalFrameUtilisateurLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jCheckBox2)
                                            .addComponent(jCheckBox3)
                                            .addComponent(jCheckBox1))
                                        .addGap(0, 24, Short.MAX_VALUE))))
                            .addGroup(jInternalFrameUtilisateurLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jBtnSuprtUtili)
                                .addGap(50, 50, 50)
                                .addComponent(jBtnAjoutUtili, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jInternalFrameUtilisateurLayout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jLabel24)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jInternalFrameUtilisateurLayout.setVerticalGroup(
            jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jInternalFrameUtilisateurLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel24)
                .addGap(18, 18, 18)
                .addComponent(jComboBoxUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18))
                .addGap(17, 17, 17)
                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTFPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19))
                .addGap(17, 17, 17)
                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel20)
                    .addComponent(jCheckBox1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jCheckBox3)
                .addGap(46, 46, 46)
                .addGroup(jInternalFrameUtilisateurLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jBtnSuprtUtili)
                    .addComponent(jBtnAjoutUtili))
                .addContainerGap(70, Short.MAX_VALUE))
        );

        getContentPane().add(jInternalFrameUtilisateur);
        jInternalFrameUtilisateur.setBounds(1230, 520, 380, 470);

        jMenu1.setText("Fichier");

        jMenuClient.setText("Client");

        jMenuItem1.setText("Nouveau Client");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenuClient.add(jMenuItem1);

        jMenuItem7.setText("Modifier Client");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenuClient.add(jMenuItem7);

        jMenu1.add(jMenuClient);

        jMenuItemProduit.setText("Produit");
        jMenuItemProduit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemProduitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemProduit);

        jMenuWilaya.setText("Wilaya");

        jMenuItem6.setText("Ajouter wilaya");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenuWilaya.add(jMenuItem6);

        jMenuItem9.setText("Supprimer wilaya");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenuWilaya.add(jMenuItem9);

        jMenu1.add(jMenuWilaya);

        jMenuItemFacture.setText("Facturation");
        jMenuItemFacture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemFactureActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemFacture);

        jMenuItemJournal.setText("Journal");
        jMenuItemJournal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemJournalActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemJournal);

        jMenuItemCalcul.setText("Calculatrice");
        jMenuItemCalcul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCalculActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemCalcul);
        jMenu1.add(jSeparator2);

        jMenuUtilisateur.setText("Utilisateur");

        jMenuItem2.setText("Nouveau utilisateur");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenuUtilisateur.add(jMenuItem2);

        jMenuItem8.setText("Supprimer utilisateur");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenuUtilisateur.add(jMenuItem8);

        jMenu1.add(jMenuUtilisateur);
        jMenu1.add(jSeparator1);

        jMenuItem10.setText("Fermer");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem10);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edition");

        jMenuUnlockPrec.setText("Facture PREC");

        jMenuItem11.setText("ON");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenuUnlockPrec.add(jMenuItem11);

        jMenuItem15.setText("OFF");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        jMenuUnlockPrec.add(jMenuItem15);

        jMenu2.add(jMenuUnlockPrec);

        jMenuUnlockNum.setText("Facture NUM");

        jMenuItem17.setText("ON");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        jMenuUnlockNum.add(jMenuItem17);

        jMenuItem18.setText("OFF");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        jMenuUnlockNum.add(jMenuItem18);

        jMenu2.add(jMenuUnlockNum);

        jMenuItemRemise0.setText("Remise à zero");
        jMenuItemRemise0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemRemise0ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItemRemise0);

        jMenuRepport.setText("Repport");

        jMenuItem16.setText("Path");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        jMenuRepport.add(jMenuItem16);

        jMenu2.add(jMenuRepport);

        jMenuBar1.add(jMenu2);

        jMenuTheme.setText("Theme");

        jMenu6.setText("Designe");

        jMenuItem12.setText("Windows thème");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem12);

        jMenuItem13.setText("Classique thème");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem13);

        jMenuItem14.setText("Métal thème");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem14);

        jMenuItem5.setText("Osux thème");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu6.add(jMenuItem5);

        jMenuTheme.add(jMenu6);

        jMenu4.setText("Arrière plan");

        jRadioButtonMenuItem1.setBackground(new java.awt.Color(255, 255, 204));
        buttonGroup1.add(jRadioButtonMenuItem1);
        jRadioButtonMenuItem1.setSelected(true);
        jRadioButtonMenuItem1.setText("Jaune");
        jRadioButtonMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem1ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem1);

        jRadioButtonMenuItem2.setBackground(new java.awt.Color(204, 255, 255));
        buttonGroup1.add(jRadioButtonMenuItem2);
        jRadioButtonMenuItem2.setText("Blue");
        jRadioButtonMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem2ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem2);

        jRadioButtonMenuItem3.setBackground(new java.awt.Color(204, 255, 204));
        buttonGroup1.add(jRadioButtonMenuItem3);
        jRadioButtonMenuItem3.setText("Verte");
        jRadioButtonMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem3ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem3);

        jRadioButtonMenuItem4.setBackground(new java.awt.Color(255, 204, 204));
        buttonGroup1.add(jRadioButtonMenuItem4);
        jRadioButtonMenuItem4.setText("Rouge");
        jRadioButtonMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem4);

        jRadioButtonMenuItem5.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(jRadioButtonMenuItem5);
        jRadioButtonMenuItem5.setText("Blanc");
        jRadioButtonMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonMenuItem5ActionPerformed(evt);
            }
        });
        jMenu4.add(jRadioButtonMenuItem5);

        jMenuTheme.add(jMenu4);

        jMenuBar1.add(jMenuTheme);

        jMenu3.setText("?");
        jMenu3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu3MouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        getAccessibleContext().setAccessibleName("EURL FAB FACTURATION");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    String ddMM = "0101";

    private final String ID2 = "Test1";
    private final Preferences prefs = Preferences.userRoot().node(this.getClass().getName());

    public void setPreference() {
        // This will define a node in which the preferences can be stored
        System.out.println(prefs.get(ID2, "C:\\Users\\PCname\\Documents\\NetBeansProjects\\FabFacture"));
    }

    private void LastNumFacture() {
        String num_fc = null;
        try {
            String sql = "SELECT num_factur FROM public.factur "
                    + "ORDER BY num_factur DESC "
                    + "LIMIT 1";

            reslt = stmt.executeQuery(sql);
            String a1 = "FAB20A/0001";
                    String b1 = "FAB20B/0001";
                          System.out.println("deee"+ a1.compareTo(b1));

            if (reslt.next()) {
                num_fc = reslt.getString("num_factur");
            }
            
                               
        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (formatter1.format(new Date()).equals(ddMM)) {
            if (num_fc == null) {
                x = 1;
                fab = "A/";
                gener(fab);
                
            } else {
                //------ 20/A/00002 -----
                String[] NSplit1 = num_fc.split("/");
                
                                                   
               // String[] NSplit2 = NSplit1[0].split("FAB");
                //int h = Integer.parseInt(NSplit1[2]+ NSplit1[0]);
                //String gen1 = formatter3.format(new Date()) + "00001";
                
                String gen1 = "FAB"+formatter3.format(new Date()) +"A/0001";
                //int g = Integer.parseInt(gen1);
                System.out.println("happy new year" + gen1 + "/" + num_fc);
                System.out.println("happy" + NSplit1[0] + ":" + NSplit1[1]);
                //hna awel facture f 3am jdid
                
                if (num_fc.compareTo(gen1)>=0) {
                    //String[] NSplit3 = num_fc.split(formatter3.format(new Date()));
                    x = Integer.parseInt(NSplit1[1]) + 1;
                    //fab = NSplit1[0]+"/";
                    String bsk = formatter3.format(sqdat());
                    String[] NSplit44 = NSplit1[0].split(bsk);
                    fab = NSplit44[1]+"/";
                    gener(fab);
                } else {
                    x = 1;
                    fab = "A/";
                    gener(fab);
                    
                    //ici 2 w 3 w ... mor la 1er facture f le 1er jr ta3 3am jdid
                }
            }

        } else {
            if (num_fc == null) {
                x = 1;
                fab = "A/";
                gener(fab);
            } else {
              
                String[] NSplit4 = num_fc.split("/");
                //String gen2 = NSplit4[0];
                //String gen3 = "FAB"+formatter3.format(new Date())+"A";
                String gen3 = formatter3.format(new Date());
                 char xh1 = NSplit4[0].charAt(3);
                 char xh2 = NSplit4[0].charAt(4);
                 String xh3 =String.valueOf(xh1) ;
                 String xh4 =String.valueOf(xh2) ;
                 String xhGlobal = xh3+xh4;
                 
                 System.out.println("hmm" +xhGlobal+ ":" + gen3);
                  if("FAB21AA/200".compareTo("FAB20ZZ/200")>=0){
                  System.out.println("1 kbir 3la 2");
                  }else{
                  System.out.println("1 sghir 3la 2");
                  }
                if(gen3.compareTo(xhGlobal)>=0){
                x = Integer.parseInt(NSplit4[1]) + 1;
                //fab = NSplit4[0]+"/";
                String bsk = formatter3.format(sqdat());
                String[] NSplit44 = NSplit4[0].split(bsk);
                                       
                 fab = NSplit44[1]+"/";
                gener(fab);
                }else{
                jTextFieldNumFct.setText("ERREUR DATE");//
                jButton9.setEnabled(false);
                }
            }
        }

    }

    public static void TableModel() {
        dt = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        dt.addColumn("designation");
        dt.addColumn("PU");
        dt.addColumn("QTT");
        dt.addColumn("TVA");
        dt.addColumn("Montant HT");
        jtableFactur.setModel(dt);
    }

    public static void TableModelD() {
        dtD = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        dtD.addColumn("designation");
        dtD.addColumn("PU");
        dtD.addColumn("QTT");
        dtD.addColumn("TVA");
        dtD.addColumn("Montant HT");
        jTableDetail.setModel(dtD);
    }


    private String numPr(JComboBox combo) {
        String num_p = "";
        try {
            String sql = "SELECT num_prod FROM public.produit "
                    + "WHERE disign_prod = '" + combo.getSelectedItem() + "'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                num_p = reslt.getString("num_prod");
                // jComboboxProduit.addItem(num_p);
                // JOptionPane.showMessageDialog(null, "2"+num_p);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }

        return num_p;

    }

    private String ExSplit(SQLException ex) {
        String exs = ex.toString();
        String[] NSplit = exs.split("Détail");
        return NSplit[1];
    }

    private String idfactr() {
        String idfactr = jTextFieldNumFct.getText() + numPr(jComboboxProduit) + formatter3.format(date);
        return idfactr;
    }

    private void ajouterFactur() {
        try {
            String sqlInsr = "INSERT INTO public.factur "
                    + "(num_factur, date_factur, quantete, num_prod, diot, "
                    + "reduction, id_facture, tva_pf, mode_paim, unt_m, destination) "
                    + //"(\"num_factur\", date_factur, quantete, \"num_prod\", \"diot\", reduction, \"id_facture\") " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
            //"VALUES ('"+jTextFieldNumFct.getText()+"', '"+formatter.format(date)+"', "+Integer.parseInt(jTextFieldQtt.getText())+", '"+jComboboxProduit.getSelectedItem()+"', '"+jTextFieldDiot.getText()+"', "+Float.parseFloat(jTextFieldReduction.getText())+",'"+idfactr+"');";
            pst = c.prepareStatement(sqlInsr);
            pst.setString(1, jTextFieldNumFct.getText());
            pst.setDate(2, sqdat());
            pst.setInt(3, Integer.parseInt(jTextFieldQtt.getText()));
            pst.setString(4, numPr(jComboboxProduit));
            pst.setString(5, fieldDiot.getText());
            pst.setFloat(6, prsntPourBD);
            pst.setString(7, idfactr());
            if (jComboBoxTVA.getSelectedIndex() == 0) {
                pst.setFloat(8, 0);
            } else {
                pst.setFloat(8, Float.parseFloat((String) jComboBoxTVA.getSelectedItem()));
            }

            if ("Mode de paiement".equals((String)jComboBoxModeP.getSelectedItem())) {
                pst.setString(9, "A terme");
            } else {
                pst.setString(9, (String) jComboBoxModeP.getSelectedItem());
            }

            if ("U/M".equals((String)jComboBoxUM.getSelectedItem())) {
                pst.setString(10, "QUINTAL");
            } else {
                pst.setString(10, (String) jComboBoxUM.getSelectedItem());
            }
            pst.setString(11, (String) jComboBoxDestination.getSelectedItem());

            pst.executeUpdate();
            pst.close();

            System.out.println("inserted");
            prsnt = 0;
            jTextFieldReduction.setText(null);
            jTextFieldQtt.setText(null);
            jTextField14.setText("Prix Unitaire");
            jComboboxProduit.setSelectedIndex(0);
            jComboboxProduit1.setSelectedIndex(0);
            jButton18.setEnabled(false);
            jButton17.setEnabled(true);
            AfficheFactur(jTextFieldNumFct.getText(), "");
        } catch (SQLException ex) {
            
            JOptionPane.showMessageDialog(null,"Vérifier l'existance de cette Diot");
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void AjoutProduit() {
        if ("".equals(jTextField1.getText()) || "".equals(jTextField2.getText()) || "".equals(jTextField3.getText())) {
            JOptionPane.showMessageDialog(null, "Remplire tous les champs !!");
        } else {
            try {
                String sqlInsr = "INSERT INTO public.produit(num_prod, disign_prod, tva_prod) VALUES (?, ?, ?)";

                pst = c.prepareStatement(sqlInsr);
                pst.setString(1, jTextField1.getText());
                pst.setString(2, jTextField2.getText());
                pst.setFloat(3, Float.parseFloat(jTextField3.getText()));
                pst.executeUpdate();
                pst.close();
                JOptionPane.showMessageDialog(null, "Produit Ajouter");
            } catch (SQLException ex) {
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    private void AjoutPrix() {
        if (jComboboxProduit2.getSelectedItem() == "Produit") {
            JOptionPane.showMessageDialog(null, "choisissiez un produit est remplire les champs !!");
        }
        if (Float.parseFloat(jTextField5.getText()) == num2) {
            JOptionPane.showMessageDialog(null, "auccun changement du prix !!");
        } else {
            try {
                String idPrix = formatter2.format(date) + numPr(jComboboxProduit2);
                String sqlInsr = "INSERT INTO public.prix_tab(num_prod, valeur_prix, date_prix, id_prix) VALUES (?, ?, ?, ?)";

                pst = c.prepareStatement(sqlInsr);
                pst.setString(1, numPr(jComboboxProduit2));
                pst.setFloat(2, Float.parseFloat(jTextField5.getText()));
                pst.setDate(3, sqdat());
                pst.setString(4, idPrix);
                pst.executeUpdate();
                pst.close();
                JOptionPane.showMessageDialog(null, "Prix modifier");
            } catch (SQLException ex) {
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        FactureDeClient();

        if ("Num Facture".equals(jTextFieldNumFct.getText()) || x == 0) {
            JOptionPane.showMessageDialog(null, "Aucun numéro pour la facture détecter");
        } else if ("".equals(fieldNam.getText()) || "".equals(fieldDiot.getText())) {
            JOptionPane.showMessageDialog(null, "vous devez saisire un client");
        } else if ("".equals(jTextFieldQtt.getText())) {
            JOptionPane.showMessageDialog(null, "vous devez choiser un produit avec une quantetée");
        } else if ("Destination".equals((String)jComboBoxDestination.getSelectedItem())) {
            JOptionPane.showMessageDialog(null, "vous devez sélectionner une destination");
        } else if (diot_f != null) {
            // JOptionPane.showMessageDialog(null, diot_f+"/"+date_f+"/"+sqdat());
            if (fieldDiot.getText().equals(diot_f) && sqdat().equals(date_f)) {
                //JOptionPane.showMessageDialog(null, "ddd");
                if (ProduitDeFacture()) {
                    JOptionPane.showMessageDialog(null, "ce produit est existe dans cette facture, si vous decidez de le modifier l'obligation de le supprimer est validée");
                } else {
                    ajouterFactur();
//                    AfficheFactur(jTextFieldNumFct.getText(), "", sqdat());// AND factur.diot='" + fieldDiot.getText() + "'
//                    FiltoJCDest_Um_Mp(jComboBoxDestination, "destination");
//                    FiltoJCDest_Um_Mp(jComboBoxModeP, "mode_paim");
//                    FiltoJCDest_Um_Mp(jComboBoxUM, "unt_m");
                }
            } else {
                JOptionPane.showMessageDialog(null, "cette facture " + jTextFieldNumFct.getText() + " est réservée par le client " + diot_f + " en date:" + date_f);
            }
        } else {
            ajouterFactur();
//            AfficheFactur(jTextFieldNumFct.getText(), "", sqdat());//AND factur.diot='" + fieldDiot.getText() + "'
//            FiltoJCDest_Um_Mp(jComboBoxDestination, "destination");
//            FiltoJCDest_Um_Mp(jComboBoxModeP, "mode_paim");
//            FiltoJCDest_Um_Mp(jComboBoxUM, "unt_m");
        }

        //NamDiot();
        //prsnt = 0; //initializer le pourcentage
    }//GEN-LAST:event_jButton3ActionPerformed


    private void Facturer(String numf, String diotf, Double numAlfa, Date ddt, String mp, String up, String dest) {
        try {

            JasperDesign jd = null;
            try {
                            jd = JRXmlLoader.load(prefs.get(ID2, "C:\\xxx\\FABFacture")+"\\Report\\FABReport.jrxml");
           System.out.println("yaw"+prefs.get(ID2, "C:\\xxx\\FABFacture"));
            } catch (JRException e) {
                System.out.println("nullll yaw"+prefs.get(ID2, "C:\\xxx\\FABFacture")+"\\Report\\FABReport.jrxml");
            }
            if (jd==null) {
                JOptionPane.showMessageDialog(null, "Véifier le chemain de rapport !! ");
            }else
            {
             JasperDesign SubJd = JRXmlLoader.load(prefs.get(ID2, "C:\\xxx\\FABFacture")+"\\Report\\SUBFABReport.jrxml");
              
            JRDesignQuery updateq = new JRDesignQuery();
            String query = "SELECT *"
                    + "	FROM client, factur, prix_tab, produit"
                    + "	WHERE prix_tab.num_prod = produit.num_prod "
                    + "	AND factur.num_prod = produit.num_prod "
                    + "	AND client.diot=factur.diot "
                    + "	AND factur.num_factur='" + numf + "'"
                    + "	AND factur.diot='" + diotf + "'"
                    + "	AND date_factur='" + ddt + "'"
                    + "	AND id_prix IN (SELECT MAX(id_prix)"
                    + "	FROM public.prix_tab"
                    + "	WHERE date_prix<='" + ddt + "'"
                    + "	GROUP BY num_prod)";
            updateq.setText(query);
            jd.setQuery(updateq);
            SubJd.setQuery(updateq);
            URL url = getClass().getResource("logofabrapportnb.png");
            String urlstr = url.toString();
            
            HashMap mesParametres = new HashMap();
            mesParametres.put("MontantAlphabetParm", convert(numAlfa));
            mesParametres.put("image", urlstr);
            mesParametres.put("DateParm", ddt);
            mesParametres.put("UM", up);
            mesParametres.put("ModePaiment", mp);
            mesParametres.put("NetTTC", Math.round(numAlfa));
            //String.valueOf(Math.round(numAlfa))
            mesParametres.put("destin", dest);
            JasperReport SubJrep = JasperCompileManager.compileReport(SubJd);
            mesParametres.put("subreportParameter_java", SubJrep);
            System.out.println("0");
            JasperReport jrep = JasperCompileManager.compileReport(jd);
            System.out.println("1");
            JasperPrint print = JasperFillManager.fillReport(jrep, mesParametres, c);
            System.out.println("2");
            JasperViewer jv = new JasperViewer(print, false);
            System.out.println("3");
            jv.setTitle("Facture");
            jv.setIconImage(new ImageIcon(this.getClass().getResource("fab.png")).getImage());
            jv.setVisible(true);
        }
        } catch (JRException e) {
            JOptionPane.showMessageDialog(null, " Resultats non prise en compte par jasper" + e);
        }
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        Facturer(jTextFieldNumFct.getText(), fieldDiot.getText(), (double) MontantRedu, sqdat(), modP, UntP, destin);

    }//GEN-LAST:event_jButton4ActionPerformed

    private int x = 0;
    private String fab ;
    private String zero = null;
    private String gen;

    private void gener(String Alphabet) {
        String xx = String.valueOf(x);
        if (xx.length() == 1) {
            zero = "00";
        }
        if (xx.length() == 2) {
            zero = "0";
        }
        if (xx.length() == 3) {
            zero = "";
        }
  
        //nzid alfabet w n7oto f base
        gen = "FAB"+formatter3.format(sqdat()) + Alphabet + zero + x;
        jTextFieldNumFct.setText(gen);
        System.out.println("gennnnnn:" + gen);
    }

    private void maxPrix() {
        String ddd = (String) jComboboxProduit.getSelectedItem();

        if ("Produit acheter".equals(ddd)) {
            jComboboxProduit.setToolTipText("Produit acheter");
        } else {
            try {

                String sql = "SELECT valeur_prix FROM public.prix_tab "
                        + "WHERE id_prix = (SELECT MAX(id_prix) FROM public.prix_tab "
                        + "WHERE date_prix <= '" + sqdat() + "' AND num_prod = '" + numPr(jComboboxProduit) + "')";
                reslt = stmt.executeQuery(sql);
                if (reslt.next()) {
                    num = reslt.getInt("valeur_prix");
                    jComboboxProduit.setToolTipText("Prix: " + num + "DA");
                    jTextField14.setText(num + " DA");
                    jComboboxProduit1.setSelectedItem(ddd);
                    //jLabel17.setText(""+num);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur" + ex);
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        jTextFieldQtt.setText(null);
        jTextField15.setText("Qtt achetée");
        jTextField16.setText("Prix de reduction");
        jTextField17.setText("Pourcentage de reduction total");
        jTextField18.setText("Montant sans reduction");
        jTextField19.setText("Prix après la reduction");
    }

    static int i;
    static float totalHT;
    static float totalTVA;
    static float reduction;
    static float MontantRedu;
    static String modP, UntP, destin;
    static final Object[] idid = new Object[10];
    static String nom_client, id_client;
    static Date datFact;

    public static void AfficheFactur(String xx, String diotrech) {
        totalTVA = 0;
        totalHT = 0;
        reduction = 0;
        MontantRedu = 0;
        modP = "Mode de paiement";
        UntP = "U/M";
        destin = "Destination";
        nom_client = null;
        id_client = null;
        datFact = new Date();
        try {
            i = 0;
            TableModel();
            TableModelD();
            String Sqlfct = "SELECT *"
                    /*+ " FROM client INNER JOIN factur INNER JOIN prix_tab INNER JOIN produit"
                    + "	WHERE prix_tab.num_prod = produit.num_prod "
                    + "	AND factur.num_prod = produit.num_prod "
                    + "	AND client.diot = factur.diot "
                    + "	AND factur.num_factur='" + xx + "'"*/
                    
                    //********** bedlt menna l hatta ***********
                    + " FROM factur"
                    + " INNER JOIN client ON client.diot=factur.diot"
                    + " INNER JOIN produit ON factur.num_prod = produit.num_prod "
                    + " INNER JOIN prix_tab ON produit.num_prod = prix_tab.num_prod"              
                    + "	WHERE factur.num_factur='" + xx + "'"
                    //************* hna *************************
                    
                    + diotrech
                    //+ "	AND date_factur='" + jour + "'"
                    + "	AND id_prix IN (SELECT MAX(id_prix)"
                    + "	FROM public.prix_tab"
                    + "	WHERE date_prix<=(SELECT date_factur FROM factur WHERE num_factur='" + xx + "' GROUP BY date_factur)"//'" + jour + "'"
                    + "	GROUP BY num_prod)";

            reslt = stmt.executeQuery(Sqlfct);
            while (reslt.next()) {
                id_client = reslt.getString("diot");
                nom_client = reslt.getString("nom");
                reduction = reduction + reslt.getFloat("reduction");
                modP = reslt.getString("mode_paim");
                UntP = reslt.getString("unt_m");
                destin = reslt.getString("destination");
                datFact = reslt.getDate("date_factur");
                String id_fact = reslt.getString("id_facture");
                String designation = reslt.getString("disign_prod");
                long prix = reslt.getLong("valeur_prix");
                int qtt = reslt.getInt("quantete");
                float tva = reslt.getFloat("tva_pf");
                //System.out.println("mode paim:"+modP);;
                totalTVA = totalTVA + tva;
                float montant = (qtt * prix);
                totalHT = totalHT + montant;
                Object[] stg = {designation, prix, qtt, tva, montant};
                idid[i] = id_fact;
                i = i + 1;
                if (jInternalFrameJournal.isVisible()) {
                    dtD.addRow(stg);
                } else {
                    dt.addRow(stg);
                }
            }
            
           
            float TotalAvcTva = totalHT + (totalHT * totalTVA / 100);
            System.out.println("9bl modp 0 ou 1" + totalHT + "/" + totalTVA + "/" + TotalAvcTva + "/" + reduction + "/" + MontantRedu);

            if ("Espece".equals(modP)) {
                if (TotalAvcTva < 100000) {
                    MontantRedu = ((TotalAvcTva - (totalHT * reduction / 100)) + (TotalAvcTva * 1 / 100));
                    System.out.println("<100000" + totalHT + "/" + TotalAvcTva + "/" + reduction + "/" + MontantRedu);
                } else {
                    MontantRedu = (TotalAvcTva - (totalHT * reduction / 100)) + 1000;
                    System.out.println(">100000" + totalHT + "/" + TotalAvcTva + "/" + reduction + "/" + MontantRedu);
                }

            } else {
                MontantRedu = (TotalAvcTva - (totalHT * reduction) / 100);
                System.out.println("non Espece" + totalHT + "/" + TotalAvcTva + "/" + reduction + "/" + MontantRedu);
            }
            
             if (jInternalFrameFactur.isVisible()) {
                    FiltoJCDes_Um_Mp();
                   jDateChooser1.setDate(datFact); 
                   /*jComboBoxDestination.setSelectedItem(destin);
                   jComboBoxModeP.setSelectedItem(modP);
                   jComboBoxUM.setSelectedItem(UntP);*/
            }

        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void NamDiot() {
        fieldDiot.setText(id_client);
        fieldNam.setText(nom_client);
    }

    public static void afichNomPrnOrDiot(String TxtFild1, JTextField TxtFild2, String clnName, String cndtion) {
        try {
           // String txt1 = TxtFild1.getText();
            String sql = "SELECT " + clnName + " FROM public.client "
                    + "WHERE " + cndtion + " = '" + TxtFild1 + "'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                String atrb = reslt.getString(clnName);
                TxtFild2.setText(atrb);
            } else {
                TxtFild2.setText("");
            }

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    
    private void jTextFieldNamAutoMouseClicked(java.awt.event.MouseEvent evt, JTextField txt) {                                            
        fieldDiot.setText("");
        fieldNam.setText("");
        txt.requestFocus();
        jLabelDel.setVisible(false);
        TableModel();
        FiltoJCDes_Um_Mp();
    }  
    
    
    private void jTextFieldNamJAutoMouseClicked(java.awt.event.MouseEvent evt, JTextField txt) {                                            
        fieldDiotJ.setText("");
        fieldNamJ.setText("");
        txt.requestFocus();
        TableModelJ();
        jDialog1.setVisible(false);
        jLabelRemarque.setText("");
        jLabelRapport.setEnabled(false);
    }  
       
    
    String txtEsah;
    final class a implements AutoCompleterCallback{
        a(FabFramFatcture paramTestWindow) {}
        @Override
        public void callback(Object o) {
            txtEsah = (String) o;
            if (jInternalFrameJournal.isVisible()) {
                afichNomPrnOrDiot(txtEsah, fieldDiotJ, "diot", "nom");
                if ("".equals(fieldDiotJ.getText())) {
                    TableModelJ();
                } else {
                    AfficheFacturJrnl();
                }
            } else {
                afichNomPrnOrDiot(txtEsah, fieldDiot, "diot", "nom");
                AfficheFactur(jTextFieldNumFct.getText(), " AND factur.diot='" + fieldDiot.getText() + "'");
            }
            }
    }


    private void jComboboxProduit1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboboxProduit1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboboxProduit1ActionPerformed

    private int num;
    private void jComboboxProduit1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboboxProduit1ItemStateChanged
        String ddd = (String) jComboboxProduit1.getSelectedItem();

        if ("Produit acheter".equals(ddd)) {

        } else {
            try {

                String sql = "SELECT valeur_prix FROM public.prix_tab "
                        + "WHERE id_prix = (SELECT MAX(id_prix) FROM public.prix_tab "
                        + "WHERE date_prix <= '" + sqdat() + "' AND num_prod = '" + numPr(jComboboxProduit1) + "');  ";
                reslt = stmt.executeQuery(sql);
                if (reslt.next()) {
                    num = reslt.getInt("valeur_prix");
                    // JOptionPane.showMessageDialog(null, sql+""+num);
                    //jComboboxProduit.setToolTipText("Prix: "+num+"DA");
                    jTextField14.setText(num + " DA");
                    //jLabel17.setText(""+num);
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur" + ex);
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        jTextField15.setText("Qtt achetée");
        jTextField16.setText("Prix de reduction");
        jTextField17.setText("Pourcentage de reduction total");
        jTextField18.setText("Montant sans reduction");
        jTextField19.setText("Prix après la reduction");

    }//GEN-LAST:event_jComboboxProduit1ItemStateChanged

    private void filToAutocomplet(){
        try {  
            
            values.clear();
            valuesD.clear();
            String sqli="SELECT nom,diot FROM public.client";
            reslt =  stmt.executeQuery(sqli);
            while(reslt.next()){
                String nn = reslt.getString("nom");
                String dd = reslt.getString("diot");
                System.out.println(dd+"/"+nn);
                values.add(nn);
                valuesD.add(dd);
            }   
        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    
    
    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        
        
            filToAutocomplet();
            jInternalFrameProduit.setVisible(false);
            jInternalFrameClient.setVisible(false);
            jInternalFrameUtilisateur.setVisible(false);
            jInternalFrameCalculatrice.setVisible(false);
            try {
                jInternalFrameJournal.setMaximum(true);
            } catch (PropertyVetoException ex) {
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }
         

    }//GEN-LAST:event_formWindowOpened

    private void formComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentResized
        System.out.println(fenster.getHeight() + "/" + fenster.getWidth());
        if (jInternalFrameJournal.isVisible()) {
            fenster.jInternalFrameClient.setVisible(false);
            fenster.jInternalFrameFactur.setVisible(false);
            fenster.jInternalFrameProduit.setVisible(false);
            fenster.jInternalFrameCalculatrice.setVisible(false);
            fenster.jInternalFrameUtilisateur.setVisible(false);
            jInternalFrameJournal.setBounds((fenster.getWidth() - 780) / 2,
                    (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        }
        jInternalFrameCalculatrice.setBounds((fenster.getWidth() - 380 - 35), (fenster.getHeight() - 70 - 470) / 2, 380, 470);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameProduit.setBounds((fenster.getWidth() - 380 - 35), (fenster.getHeight() - 70 - 470) / 2, 380, 470);
        jInternalFrameClient.setBounds((fenster.getWidth() - 380 - 35), (fenster.getHeight() - 70 - 470) / 2, 380, 470);
        jInternalFrameUtilisateur.setBounds((fenster.getWidth() - 380 - 35), (fenster.getHeight() - 70 - 470) / 2, 380, 470);


    }//GEN-LAST:event_formComponentResized

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        System.out.println("state de frame P: " + evt.getNewState());
    }//GEN-LAST:event_formWindowStateChanged

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        try {
            c.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowClosed

    private void jInternalFrameCalculatricePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jInternalFrameCalculatricePropertyChange

    }//GEN-LAST:event_jInternalFrameCalculatricePropertyChange

    private void jInternalFrameCalculatriceComponentMoved(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jInternalFrameCalculatriceComponentMoved
        //System.out.println( fenster.getHeight() +"/"+ fenster.getWidth());
        System.out.println(jInternalFrameCalculatrice.getBounds());
        // 810, 10, 380, 470


    }//GEN-LAST:event_jInternalFrameCalculatriceComponentMoved

    private void jMenuItemProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemProduitActionPerformed
        jInternalFrameJournal.setVisible(false);
        jInternalFrameCalculatrice.setVisible(false);
        jInternalFrameClient.setVisible(false);
        jInternalFrameUtilisateur.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameProduit.setVisible(true);
    }//GEN-LAST:event_jMenuItemProduitActionPerformed

    private void jMenuItemCalculActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCalculActionPerformed
        jInternalFrameJournal.setVisible(false);
        jInternalFrameProduit.setVisible(false);
        jInternalFrameClient.setVisible(false);
        jInternalFrameUtilisateur.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameCalculatrice.setVisible(true);

        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItemCalculActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        jInternalFrameProduit.setVisible(false);
        jInternalFrameCalculatrice.setVisible(false);
        jInternalFrameUtilisateur.setVisible(false);
        jInternalFrameJournal.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameClient.setVisible(true);
        jButton16.setEnabled(false);
        jButton15.setEnabled(true);
        FiltoJCClient();

    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        AjoutProduit();
        FiltoJCombo(jComboboxProduit, "Produit acheter");
        FiltoJCombo(jComboboxProduit1, "Produit acheter");
        FiltoJCombo(jComboboxProduit2, "Produit");

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed

        try {
            String sql = "SELECT valeur_prix FROM public.prix_tab "
                    + "WHERE num_prod = (SELECT num_prod FROM public.produit "
                    + "WHERE disign_prod= '" + (String) jComboboxProduit2.getSelectedItem() + "') AND date_prix = '" + new Date() + "'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                num = reslt.getInt("valeur_prix");
                JOptionPane.showMessageDialog(null, "Vous pouvez ajouter seulement une seul valeur de prix au meme produit par jour ");
            } else {
                AjoutPrix();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton11ActionPerformed


    private void jTextField15KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyReleased
        if ("".equals(jTextField15.getText())) {
            jTextField18.setText("Montant sans reduction");
        } else {
            float m1 = (Integer.parseInt(jTextField15.getText())) * (num);
            jTextField18.setText(m1 + " DA");
        }
    }//GEN-LAST:event_jTextField15KeyReleased

    private float prsnt = 0;
    private void jTextField16KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyReleased
        if ("".equals(jTextField16.getText())) {
            jTextField17.setText("Pourcentage de reduction total");
            jTextField19.setText("Prix après la reduction");
        } else {
            float m1 = (Integer.parseInt(jTextField15.getText())) * (num);
            float m2 = (Integer.parseInt(jTextField15.getText())) * (Float.parseFloat(jTextField16.getText()));
            prsnt = 100 - (m2 * 100 / m1);

            jTextField19.setText(m2 + " DA");
            jTextField17.setText(prsnt + " %");
        }
    }//GEN-LAST:event_jTextField16KeyReleased

    private float prsntPourBD = 0;
    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        jTextFieldReduction.setText(prsnt + " %");
        prsntPourBD = prsnt;
        jButton18.setEnabled(true);
        jButton17.setEnabled(false);
    }//GEN-LAST:event_jButton17ActionPerformed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        jTextFieldReduction.setText("");
        prsntPourBD = 0;
        jButton18.setEnabled(false);
        jButton17.setEnabled(true);
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField15ActionPerformed

    private void jTextField16MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField16MouseEntered

    }//GEN-LAST:event_jTextField16MouseEntered

    private void jTextField19MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField19MousePressed

    }//GEN-LAST:event_jTextField19MousePressed

    private void jTextField17MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField17MousePressed

    }//GEN-LAST:event_jTextField17MousePressed

    private void jTextField16MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField16MousePressed
        jTextField16.setText(null);

    }//GEN-LAST:event_jTextField16MousePressed

    private void jTextField15MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField15MousePressed
        jTextField15.setText(null);
    }//GEN-LAST:event_jTextField15MousePressed

    private float num2;
    private void jComboboxProduit2ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboboxProduit2ItemStateChanged

        String ddd = (String) jComboboxProduit2.getSelectedItem();

        if ("Produit".equals(ddd)) {
            jTextField5.setToolTipText("Prix DA");
            jTextField5.setText("Ajouter un prix");
        } else {

            try {

                String sql = "SELECT valeur_prix FROM public.prix_tab "
                        + "WHERE id_prix = (SELECT MAX(id_prix) FROM public.prix_tab "
                        + "WHERE date_prix <= '" + sqdat() + "' AND num_prod = '" + numPr(jComboboxProduit2) + "');  ";
                reslt = stmt.executeQuery(sql);
                if (reslt.next()) {
                    num2 = reslt.getFloat("valeur_prix");
                    // JOptionPane.showMessageDialog(null, num2);
                    jTextField5.setToolTipText("Prix: " + num2 + "DA");
                    jTextField5.setText("" + num2);
                } else {
                    jTextField5.setText("Ajouter un prix");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur" + ex);
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jComboboxProduit2ItemStateChanged

    private void jComboboxProduit2MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboboxProduit2MouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboboxProduit2MouseEntered

    private void jComboboxProduit2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboboxProduit2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboboxProduit2ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            String sllmod = "UPDATE public.produit SET disign_prod='" + jTextField2.getText() + "', tva_prod='" + Float.parseFloat(jTextField3.getText()) + "' WHERE num_prod='" + jTextField1.getText() + "'";

            stmt1.executeUpdate(sllmod);
            System.out.println(sllmod);
            JOptionPane.showMessageDialog(null, "updated");
            //stmt1.close();            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }

        FiltoJCombo(jComboboxProduit, "Produit acheter");
        FiltoJCombo(jComboboxProduit1, "Produit acheter");
        FiltoJCombo(jComboboxProduit2, "Produit");

    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyReleased
        try {
            String sql = "SELECT disign_prod, tva_prod FROM public.produit "
                    + "WHERE num_prod = '" + jTextField1.getText() + "'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                jTextField2.setText(reslt.getString("disign_prod"));
                jTextField3.setText("" + reslt.getFloat("tva_prod"));
            } else {
                jTextField3.setText(null);
                jTextField2.setText(null);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jTextField1KeyReleased

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased

        String txtf = jTextField2.getText();
        if ("".equals(txtf)) {
            jTextField1.setText(null);
            jTextField3.setText(null);
        } else {
            try {
                String sql = "SELECT num_prod, tva_prod FROM public.produit "
                        + "WHERE disign_prod = '" + txtf + "'";
                reslt = stmt.executeQuery(sql);
                if (reslt.next()) {
                    jTextField1.setText(reslt.getString("num_prod"));
                    jTextField3.setText("" + reslt.getFloat("tva_prod"));
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Erreur" + ex);
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jTextField2KeyReleased

    private void AjouterCleint() {
        try {

            String sqlInsr = "INSERT INTO public.client(nom, adress, dateabon, diot, activite, nif, ai, rc_cartflah) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            pst = c.prepareStatement(sqlInsr);
            //pst.setString(1, jTFnom.getText()+";"+jTFprenom.getText());
            pst.setString(1, jTFnom.getText());
            pst.setString(2, jTFadress.getText());
            pst.setDate(3, sqdat());
            pst.setString(4, jTFdiot.getText());
            pst.setString(5, jTFactivite.getText());
            pst.setString(6, jTFnif.getText());
            pst.setString(7, jTFai.getText());
            pst.setString(8, jTFrc.getText());
            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(null, "Client ajouté");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    private void FiltoJCClient() {
        try {
            jComboClient.removeAllItems();
            jComboClient.addItem("Listes des clients");
            String sql = "SELECT * FROM public.client";
            reslt = stmt.executeQuery(sql);
            while (reslt.next()) {
                String nomC = reslt.getString("nom");
                jComboClient.addItem(nomC);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed

        
            if ("".equals(jTFdiot.getText()) || "".equals(jTFnom.getText())
                    || "".equals(jTFadress.getText()) || "".equals(jTFactivite.getText())
                    || "".equals(jTFrc.getText())) {
                
                JOptionPane.showMessageDialog(null, "Sauf AI et NIF peut laisser vide");
            } else {
                
                AjouterCleint();
                jTFdiot.setText("");
                jTFnom.setText("");
                jTFactivite.setText("");
                jTFadress.setText("");
                jTFai.setText("");
                jTFnif.setText("");
                jTFrc.setText("");
                FiltoJCClient();
                filToAutocomplet();
            }
            
            
    }//GEN-LAST:event_jButton15ActionPerformed

    private void ModifieCleint() {
        try {
            String tehteh = jComboClient.getSelectedItem().toString();
            String sqlInsr = "UPDATE public.client SET diot='" + jTFdiot.getText() + "' ,nom ='" + jTFnom.getText() + "'"
                    + " , adress='" + jTFadress.getText() + "', dateabon='" + sqdat() + "', activite='" + jTFactivite.getText() + "'"
                    + " , nif='" + jTFnif.getText() + "' , ai='" + jTFai.getText() + "' , rc_cartflah='" + jTFrc.getText() + "'"
                    + " WHERE nom='" + tehteh + "'";

            int upd = stmt1.executeUpdate(sqlInsr);
            if (upd == 1) {
                System.out.println(sqlInsr);
                JOptionPane.showMessageDialog(null, "Données modifiées");
                FiltoJCClient();
                filToAutocomplet();
            } else {
                System.out.println(sqlInsr);
                JOptionPane.showMessageDialog(null, "Vous pouvez pas ajouter un client sur une page de modification");
            }

            //stmt1.close();   
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,ex.getMessage());
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex.getMessage());
        }
    }
    
    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        if ("".equals(jTFdiot.getText()) || "".equals(jTFnom.getText())
                || "".equals(jTFadress.getText()) || "".equals(jTFactivite.getText())
                || "".equals(jTFrc.getText())) {

            JOptionPane.showMessageDialog(null, "Sauf AI et NIF peut laisser vide");
        } else {

            ModifieCleint();
            jTFdiot.setText("");
            jTFnom.setText("");
            jTFactivite.setText("");
            jTFadress.setText("");
            jTFai.setText("");
            jTFnif.setText("");
            jTFrc.setText("");
        }
    }//GEN-LAST:event_jButton16ActionPerformed

    private void afficheClient(JTextField TxtFild1, JTextField TxtFild2, String clnName, String cndtion) {
        try {
            String txt1 = TxtFild1.getText();
            String sql = "SELECT " + clnName + ", adress, activite, nif, ai, rc_cartflah FROM public.client "
                    + "WHERE " + cndtion + " = '" + txt1 + "' ";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                TxtFild2.setText(reslt.getString(clnName));
                jTFadress.setText(reslt.getString("adress"));
                jTFactivite.setText(reslt.getString("activite"));
                jTFnif.setText("" + reslt.getString("nif"));
                jTFai.setText(reslt.getString("ai"));
                jTFrc.setText(reslt.getString("rc_cartflah"));
                // JOptionPane.showMessageDialog(null, num);
            } else {
                TxtFild2.setText("");
                jTFactivite.setText("");
                jTFadress.setText("");
                jTFnif.setText("");
                jTFai.setText("");
                jTFrc.setText("");

            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void afficheClientParNom(JTextField TxtFild1, JTextField TxtFild2, String clnName, String cndtion) {
        try {
            String txt1 = TxtFild1.getText();
            if ("".equals(txt1)) {
                TxtFild2.setText("");
                jTFactivite.setText("");
                jTFadress.setText("");
                jTFnif.setText("");
                jTFai.setText("");
                jTFrc.setText("");
            } else {
                String sql = "SELECT " + clnName + ", adress, activite, nif, ai, rc_cartflah FROM public.client "
                        + "WHERE " + cndtion + " = '" + txt1 + "' ";
                reslt = stmt.executeQuery(sql);
                if (reslt.next()) {
                    TxtFild2.setText(reslt.getString(clnName));
                    jTFadress.setText(reslt.getString("adress"));
                    jTFactivite.setText(reslt.getString("activite"));
                    jTFnif.setText("" + reslt.getString("nif"));
                    jTFai.setText(reslt.getString("ai"));
                    jTFrc.setText(reslt.getString("rc_cartflah"));
                    // JOptionPane.showMessageDialog(null, num);
                } else {

                }
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void jTFdiotKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFdiotKeyReleased
        afficheClient(jTFdiot, jTFnom, "nom", "diot");
    }//GEN-LAST:event_jTFdiotKeyReleased

    private void jTFnomKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFnomKeyReleased
        //afficheClientParNom(jTFnom, jTFdiot, "diot", "nom");
    }//GEN-LAST:event_jTFnomKeyReleased

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        jInternalFrameProduit.setVisible(false);
        jInternalFrameCalculatrice.setVisible(false);
        jInternalFrameUtilisateur.setVisible(false);
        jInternalFrameJournal.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameClient.setVisible(true);
        jButton15.setEnabled(false);
        jButton16.setEnabled(true);
        FiltoJCClient();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private int index;
    private void jtableFacturMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableFacturMouseClicked
        index = jtableFactur.getSelectedRow();
        jLabelDel.setVisible(true);
        //jButton6.setEnabled(true);
        //JOptionPane.showMessageDialog(null, index);

    }//GEN-LAST:event_jtableFacturMouseClicked

    private void jInternalFrameFacturComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jInternalFrameFacturComponentResized
        int x1 = jInternalFrameFactur.getX();
        int y1 = jInternalFrameFactur.getY();
        if (x1 == 0 && y1 == 0) {
            jInternalFrameCalculatrice.setVisible(false);
            jInternalFrameProduit.setVisible(false);
            jInternalFrameClient.setVisible(false);
            jInternalFrameUtilisateur.setVisible(false);
        } else {
            jInternalFrameFactur.setBounds((fenster.getWidth() - 780) / 2, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        }
    }//GEN-LAST:event_jInternalFrameFacturComponentResized

    private void jLabelDelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDelMouseExited
        jLabelDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/Sans.png"))); // NOI18N
    }//GEN-LAST:event_jLabelDelMouseExited

    private void jLabelDelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDelMouseEntered
        jLabelDel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/San.png"))); // NOI18N
    }//GEN-LAST:event_jLabelDelMouseEntered

    private void jLabelDelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelDelMouseClicked
        String message = "Voulez-vous vraiment supprimer cette facture?";
        int option = JOptionPane.showConfirmDialog(null, message, "Alerte", JOptionPane.OK_CANCEL_OPTION);
        if (option == -1) {
            //txtPathReport.setText(prefs.get(ID2, "C:\\xxx\\xxx\\FabFacture\\Report\\FABReport.jrxml"));
        } else {
            if (option == JOptionPane.CANCEL_OPTION) {
                //txtPathReport.setText(prefs.get(ID2, "C:\\xxx\\xxx\\FabFacture\\Report\\FABReport.jrxml"));
            } else {
                try {
                    String idfc = (String) idid[index];
                    String sqlsup = "DELETE FROM public.factur WHERE id_facture='" + idfc + "'";
                    stmt1.executeUpdate(sqlsup);
                    System.out.println(sqlsup);
                    //JOptionPane.showMessageDialog(null, "facture supprimer");
                    jLabelDel.setVisible(false);
                    //jButton6.setEnabled(false);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur" + ex);
                    Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
                }
                AfficheFactur(jTextFieldNumFct.getText(), "");
                NamDiot();
            }
        }
        
    }//GEN-LAST:event_jLabelDelMouseClicked

    private void jComboboxProduitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboboxProduitActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboboxProduitActionPerformed

    private void jComboboxProduitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jComboboxProduitMouseEntered

    }//GEN-LAST:event_jComboboxProduitMouseEntered

    private void seClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_seClicked

    }//GEN-LAST:event_seClicked

    private void jComboboxProduitItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboboxProduitItemStateChanged
        maxPrix();
        if (jComboboxProduit.getSelectedIndex() == 0) {
            jTextFieldQtt.setEnabled(false);
        } else
            jTextFieldQtt.setEnabled(true);
    }//GEN-LAST:event_jComboboxProduitItemStateChanged

    private void jTextFieldQttKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQttKeyTyped
        try {
            if ("Quantétes acheter".equals(jTextFieldQtt.getText())) {
                jTextFieldQtt.setText("");
            }
            char e = evt.getKeyChar();

            if ((!(Character.isDigit(e)))
                    && // Only digits
                    (e != '\b')) // For backspace
            {
                evt.consume();
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jTextFieldQttKeyTyped

    private void jTextFieldQttKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldQttKeyReleased
        try {
            jTextField15.setText(jTextFieldQtt.getText());
            jTextField18.setText((Integer.parseInt(jTextFieldQtt.getText())) * (num) + " DA");
        } catch (NumberFormatException e) {
        }

    }//GEN-LAST:event_jTextFieldQttKeyReleased

    private void jTextFieldQttMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQttMousePressed
        jTextFieldQtt.setText("");
    }//GEN-LAST:event_jTextFieldQttMousePressed

    private void jTextFieldQttMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQttMouseExited
        if ("Quantétes acheter".equals(jTextFieldQtt.getText())) {
            jTextFieldQtt.setText("");
        }
    }//GEN-LAST:event_jTextFieldQttMouseExited

    private void jTextFieldQttMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldQttMouseEntered
        if ("".equals(jTextFieldQtt.getText())) {
            jTextFieldQtt.setText("Quantétes acheter");
        }
    }//GEN-LAST:event_jTextFieldQttMouseEntered

    private void jTextFieldReductionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldReductionKeyTyped
        /* char e = evt.getKeyChar();
        if (e != '.') {
            if ((!(Character.isDigit(e))) && // Only digits
                (e != '\b')) // For backspace
            {
                evt.consume();
            }
        }*/
    }//GEN-LAST:event_jTextFieldReductionKeyTyped

    private void jTextFieldReductionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldReductionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldReductionActionPerformed

    private void jTextFieldReductionMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldReductionMousePressed
        /*jTextFieldReduction.setText("");*/
    }//GEN-LAST:event_jTextFieldReductionMousePressed

    private void jTextFieldReductionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldReductionMouseExited
        if ("Remise %".equals(jTextFieldReduction.getText())) {
            jTextFieldReduction.setText("");
        }
    }//GEN-LAST:event_jTextFieldReductionMouseExited

    private void jTextFieldReductionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextFieldReductionMouseEntered
        if ("".equals(jTextFieldReduction.getText())) {
            jTextFieldReduction.setText("Remise %");
        }
    }//GEN-LAST:event_jTextFieldReductionMouseEntered

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        System.out.println("avant" + x);
        x = x - 1;
        gener(fab);
        AfficheFactur(gen, "");
        NamDiot();
        System.out.println("apres" + x);

        jLabelDel.setVisible(false);

        if (x < 2) {
            jButton8.setEnabled(false);
        }/*else{
            gener();
            AfficheFactur(jTextFieldNumFct.getText(),"");
            NamDiot();}*/
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        LastNumFacture();
        //x = x + 1;
        //System.out.println("dkhal" + x);
        //gener(fab);
        
        //AfficheFactur(jTextFieldNumFct.getText(), " AND factur.diot='" + fieldDiot.getText() + "'", sqdat());
        AfficheFactur(gen, "");
        NamDiot();
        jLabelDel.setVisible(false);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
       
        x = 1;
        String hadi = jTextFieldNumFct.getText();
        String[] NSplit8 = hadi.split("/");
        String[] NSplit88 = NSplit8[0].split("FAB"+formatter3.format(sqdat()));
        char xh = NSplit88[1].charAt(0);
        
        if(String.valueOf(xh).compareTo("Z")==0){
          fab = "A/";
        }else{
          fab = String.valueOf(++xh)+"/";
        }
        
        gener(fab);
        AfficheFactur(jTextFieldNumFct.getText(), "");
        NamDiot();
        jButton8.setEnabled(false);
        jLabelDel.setVisible(false);
    }//GEN-LAST:event_jButton7ActionPerformed
  
    public static void TableModelJ() {
        dtJ = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        dtJ.addColumn("Client");
        dtJ.addColumn("Num facture");
        dtJ.addColumn("Date facture");
        dtJ.addColumn("Qtt");
        dtJ.addColumn("Net TTC");
        jtableJournal.setModel(dtJ);
    }

    static int k;
    static float MontantReduJ;
    static Object[][] ididJ;
    static final long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;

    public static void AfficheFacturForJ(String diotrech) {
        Long gg = (sqdatFin().getTime() - sqdatDebut().getTime());
        int totlcount = 0;
        int QttGlbl = 0;
        double MontantReduJTotl = 0;
        jLabelRemarque.setText("");
        jLabelRapport.setEnabled(false);
        try {
            k = 0;
            ididJ = new Object[150000][7];
            TableModelJ();
            String query = "SELECT deuxtab.diot, nom ,numf, datef, mode_paim,unt_m,destination, SUM(qtt) AS sumqtt, "
                    + " SUM(tva) AS sumtva, SUM(reduction) AS SumRemisePrcentg, "
                    + "	SUM((qtt*valeur_prix)+(qtt*valeur_prix*tva/100)) AS totalht_tva, "
                    + "	SUM((qtt*valeur_prix*reduction/100)) AS SUMRemiseDA "
                    + "	FROM (SELECT diot, nom ,num_factur AS numf, date_factur AS datef, "
                    + "	 tabtri.num_prod AS prod, MAX(prix_tab.id_prix) AS idpr, qtt , tva,reduction,mode_paim,unt_m,destination "
                    + "	FROM (SELECT factur.diot, nom,num_factur, date_factur, "
                    + "	factur.num_prod, id_prix, quantete AS qtt, tva_pf AS tva, reduction,mode_paim,unt_m,destination "
                    + "	FROM factur, client, prix_tab "
                    //+ " INNER JOIN client"
                    + "	WHERE client.diot=factur.diot"
                    //+ " INNER JOIN prix_tab "
                    + diotrech
                    //+ "AND client.diot='"+jTextFieldDiotJrnl.getText()+"'"
                    + "	AND factur.num_prod = prix_tab.num_prod"
                    + "	AND date_factur BETWEEN '" + sqdatDebut() + "' AND '" + sqdatFin() + "' "
                    + "	GROUP BY num_factur, nom, factur.diot, date_factur,prix_tab.id_prix, "
                    + "	factur.num_prod,qtt,tva,reduction,mode_paim,unt_m,destination) AS tabtri, prix_tab "
                    + "	WHERE prix_tab.date_prix<=tabtri.date_factur "
                    + "	AND prix_tab.id_prix = tabtri.id_prix "
                    + "	GROUP BY nom,tabtri.diot, numf, datef, prod,qtt , tva,reduction,mode_paim,unt_m,destination) AS deuxtab, prix_tab "
                    + "	WHERE deuxtab.idpr = prix_tab.id_prix "
                    + "	GROUP BY numf,nom,datef,mode_paim,unt_m,destination,deuxtab.diot ";
            reslt = stmt.executeQuery(query);
            while (reslt.next()) {
                totlcount = totlcount + 1;
                String diot_fj = reslt.getString("diot");
                String clientJ = reslt.getString("nom");
                String num_fj = reslt.getString("numf");
                Date datj = reslt.getDate("datef");
                String mode_paim = reslt.getString("mode_paim");
                String untPm = reslt.getString("unt_m");
                String destt = reslt.getString("destination");
                Integer qtt = reslt.getInt("sumqtt");
                Float totalBtva = reslt.getFloat("totalht_tva");
                Float sumRedctDA = reslt.getFloat("SUMRemiseDA");
                if ("Espece".equals(mode_paim)) {
                    if (totalBtva < 100000) {
                        MontantReduJ = ((totalBtva - sumRedctDA) + (totalBtva * 1 / 100));
                    } else {
                        MontantReduJ = (totalBtva - sumRedctDA) + 1000;
                    }
                } else {
                    MontantReduJ = (totalBtva - sumRedctDA);
                    //System.out.println( "non Espece"+ totalHT +"/"+TotalAvcTva +"/"+reduction+"/"+MontantRedu);
                }

                QttGlbl = QttGlbl + qtt;
                MontantReduJTotl = MontantReduJTotl + MontantReduJ;

                Object[] stg = {clientJ, num_fj, datj, qtt, MontantReduJ};
                dtJ.addRow(stg);
                //dettaile par semain maxumum 518400001 is 07 jours
                //par ce que tab[][] rani dayro 5000 ligne yessma aktar 
                //m semana bezzf 3lih
                //bdlto 3am yaaficher 150000 registrement par ans max

                if (gg <= Long.parseLong("31536000001")) {
                    ididJ[k][0] = num_fj;
                    ididJ[k][1] = datj;
                    ididJ[k][2] = diot_fj;
                    ididJ[k][3] = mode_paim;
                    ididJ[k][4] = untPm;
                    ididJ[k][5] = (double) MontantReduJ;
                    ididJ[k][6] = destt;

                    System.out.println(gg + "/" + ididJ[k][0] + "/" + ididJ[k][1] + "/" + ididJ[k][2] + "/" + ididJ[k][3] + "/" + ididJ[k][4] + "/" + ididJ[k][5] + "/" + ididJ[k][6]);
                    k = k + 1;
                }
                System.out.println(gg);
            }

            Long nbrJr = gg / MILLISECONDS_PER_DAY;
            Object[] stg = {"Totale", totlcount, nbrJr, QttGlbl, MontantReduJTotl};
            dtJ.addRow(stg);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private int indx;
    private void jtableJournalMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jtableJournalMouseClicked

        if (k == 0) {
            jLabelRemarque.setText(" Pour afficher les dettaile vous devez mettre une période de 365 jours max.");
            jLabelRapport.setEnabled(false);

        } else {
            //dettail
            jLabelRemarque.setText("");
            jLabelRapport.setEnabled(true);
            indx = jtableJournal.getSelectedRow();
            System.out.println(ididJ[indx][0] + "/" + ididJ[indx][1]);
            String numfc = (String) ididJ[indx][0];
            Date dd = (Date) ididJ[indx][1];
            if (numfc != null) {
                AfficheFactur(numfc, "");
            } else {
                TableModelD();
            }

            //             jDialog1.setVisible(true);
            /*int xx = jInternalFrameJournal.getX() + jPanel6.getX() + jPanel7.getX() + 5;
            int yy = (jPanel7.getY() + jPanel7.getHeight() - 10);
            jDialog1.setBounds(xx, yy, 520, 110);*/
            int xx = fenster.getX()+jPanel7.getX()+jPanel6.getX()+15;
            int yy = ((fenster.getY()+jPanel7.getY() + jPanel6.getY() + jPanel7.getHeight()) - 25);
            //jDialog1.setBounds(xx, yy , 520, 110);           
            System.out.println(xx + "/" + yy);
            
            final Point los = jtableJournal.getLocationOnScreen ();
            jDialog1.setSize(520, 110);
            jDialog1.setLocation ( los.x-3, los.y + 170);
            jDialog1.setVisible(true);
        }
    }

    private void jInternalFrameJournalComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jInternalFrameJournalComponentResized

        int x1 = jInternalFrameJournal.getX();
        int y1 = jInternalFrameJournal.getY();
        if (x1 != 0 && y1 != 0) {
            jInternalFrameJournal.setBounds((fenster.getWidth() - 780) / 2, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        }
    }//GEN-LAST:event_jInternalFrameJournalComponentResized

    public static void AfficheFacturJrnl() {
        TableModelJ();
        jDialog1.setVisible(false);
        if (sqdatDebut() == null || sqdatFin() == null) {
            //JOptionPane.showMessageDialog(null, "Entrer une date valide dans les champs Date");
        } else if ("".equals(fieldDiotJ.getText()) || "".equals(fieldNamJ.getText())) {
            AfficheFacturForJ("");
        } else if (!"".equals(fieldDiotJ.getText()) && !"".equals(fieldNamJ.getText())) {
            AfficheFacturForJ(" AND factur.diot='" + fieldDiotJ.getText() + "' ");
        }
    }
    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        AfficheFacturJrnl();
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jMenuItemJournalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemJournalActionPerformed
        jInternalFrameFactur.setVisible(false);
        jInternalFrameCalculatrice.setVisible(false);
        jInternalFrameClient.setVisible(false);
        jInternalFrameProduit.setVisible(false);
        jInternalFrameUtilisateur.setVisible(false);
        try {
            jInternalFrameJournal.setMaximum(true);
        } catch (PropertyVetoException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
        jInternalFrameJournal.setVisible(true);
    }//GEN-LAST:event_jMenuItemJournalActionPerformed

    private void jMenuItemFactureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemFactureActionPerformed
        jInternalFrameJournal.setVisible(false);
        jInternalFrameClient.setVisible(false);
        jInternalFrameProduit.setVisible(false);
        jInternalFrameUtilisateur.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameCalculatrice.setVisible(true);
    }//GEN-LAST:event_jMenuItemFactureActionPerformed

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static boolean isMac() {
        return (OS.contains("mac"));
    }

    public static boolean isUnix() {
        return (OS.contains("nix") || OS.contains("nux") || OS.indexOf("aix") > 0);
    }

    public static boolean isSolaris() {
        return (OS.contains("sunos"));
    }

    private void OsType() {

        System.out.println(OS);

        if (isWindows()) {
            System.out.println("This is Windows");
            jMenuTheme.setEnabled(true);
        } else if (isMac()) {
            System.out.println("This is Mac");
            jMenuTheme.setEnabled(false);
        } else if (isUnix()) {
            System.out.println("This is Unix or Linux");
            jMenuTheme.setEnabled(false);
        } else if (isSolaris()) {
            System.out.println("This is Solaris");
            jMenuTheme.setEnabled(false);
        } else {
            System.out.println("Your OS is not support!!");
            jMenuTheme.setEnabled(false);
        }
    }


    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FabFramFatcture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(fenster);
        //fenster.pack();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("CDE/Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FabFramFatcture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(fenster);
        //fenster.pack();
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Metal".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FabFramFatcture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(fenster);
        //fenster.pack();
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FabFramFatcture.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        SwingUtilities.updateComponentTreeUI(fenster);
        //fenster.pack();
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        jButton8.setEnabled(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        jButton8.setEnabled(false);
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jLabel1ExcelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1ExcelMouseClicked

        jDialog2.setSize(650, 210);
        jDialog2.setLocationRelativeTo(jInternalFrameJournal);
        jDialog2.setVisible(true);

    }//GEN-LAST:event_jLabel1ExcelMouseClicked

    private void jLabelRapportMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRapportMouseClicked
        jDialog1.dispose();
        if (ididJ[indx][0] != null) {
            Facturer((String) ididJ[indx][0], (String) ididJ[indx][2], (Double) ididJ[indx][5], (Date) ididJ[indx][1], (String) ididJ[indx][3], (String) ididJ[indx][4], (String) ididJ[indx][6]);
        }
    }//GEN-LAST:event_jLabelRapportMouseClicked

    private void jLabelRapportMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRapportMouseEntered
        jLabelRapport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/rapporttt.png")));
    }//GEN-LAST:event_jLabelRapportMouseEntered

    private void jLabelRapportMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabelRapportMouseExited
        jLabelRapport.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/rapport.png")));
    }//GEN-LAST:event_jLabelRapportMouseExited

    private void jLabel1ExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1ExcelMouseEntered
        jLabel1Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/excell.png")));
    }//GEN-LAST:event_jLabel1ExcelMouseEntered

    private void jLabel1ExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel1ExcelMouseExited
        jLabel1Excel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fabfacture/excel.png")));
    }//GEN-LAST:event_jLabel1ExcelMouseExited

    private void jBtnAjoutUtiliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnAjoutUtiliActionPerformed

        if ("".equals(jTFUtilisateur.getText()) || "".equals(jTFPass.getText())) {
            JOptionPane.showMessageDialog(null, "Remplire les champs Utilisateur et Mot de passe !!");
        } else {
            try {
                String sqlInsr = "INSERT INTO public.utilisateur(nomutil, pass, read, write, execution) VALUES (?, ?, ?, ?, ?)";

                pst = c.prepareStatement(sqlInsr);
                pst.setString(1, jTFUtilisateur.getText());
                pst.setString(2, jTFPass.getText());
                pst.setBoolean(3, jCheckBox1.isSelected());
                pst.setBoolean(4, jCheckBox2.isSelected());
                pst.setBoolean(5, jCheckBox3.isSelected());
                pst.executeUpdate();
                pst.close();
                JOptionPane.showMessageDialog(null, "Utilisateur Ajouter");
                FiltoJComboUtilisateur();
            } catch (SQLException ex) {
                Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }//GEN-LAST:event_jBtnAjoutUtiliActionPerformed

    private void jBtnSuprtUtiliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBtnSuprtUtiliActionPerformed
        String nmUtl = jTFUtilisateur.getText();
        if ("eurlfab".equals(nmUtl)) {
                JOptionPane.showMessageDialog(null, "Ne peut pas supprimer l'administrateur, contacter le dévloppeur");
        }else if ("".equals(nmUtl)) {
                JOptionPane.showMessageDialog(null, "Choisissez ou Tappez un nom d'utilisateur");
        }else{    
        try {
            String sqlsup = "DELETE FROM public.utilisateur WHERE nomutil='" + nmUtl + "'";
            stmt1.executeUpdate(sqlsup);
            System.out.println(sqlsup);
            JOptionPane.showMessageDialog(null, "Utilisateur supprimer");
            FiltoJComboUtilisateur();
            
        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }//GEN-LAST:event_jBtnSuprtUtiliActionPerformed

    private void RechercheUtilisateur(String tfu){
        
        try {
            String sqlsup = "SELECT * FROM public.utilisateur WHERE nomutil='" +tfu+ "'";
            reslt = stmt.executeQuery(sqlsup);
            if (reslt.next()) {
                jTFUtilisateur.setText(reslt.getString("nomutil"));
                jTFPass.setText(reslt.getString("pass"));
                jCheckBox2.setSelected(reslt.getBoolean("write"));
                jCheckBox3.setSelected(reslt.getBoolean("execution"));
            } else {
                jTFPass.setText("");
                jCheckBox2.setSelected(false);
                jCheckBox3.setSelected(false);
            }
        } catch (SQLException ex) {
            // JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
    private void jTFUtilisateurKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFUtilisateurKeyReleased
        RechercheUtilisateur(jTFUtilisateur.getText());
        
    }//GEN-LAST:event_jTFUtilisateurKeyReleased

    private void jTFPassKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTFPassKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_jTFPassKeyReleased

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed

        jInternalFrameProduit.setVisible(false);
        jInternalFrameCalculatrice.setVisible(false);
        jInternalFrameClient.setVisible(false);
        jInternalFrameJournal.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameUtilisateur.setVisible(true);
        FiltoJComboUtilisateur();
        jBtnAjoutUtili.setEnabled(true);
        jBtnSuprtUtili.setEnabled(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        jInternalFrameProduit.setVisible(false);
        jInternalFrameCalculatrice.setVisible(false);
        jInternalFrameClient.setVisible(false);
        jInternalFrameJournal.setVisible(false);
        jInternalFrameFactur.setBounds(25, (fenster.getHeight() - 70 - 470) / 2, 780, 470);
        jInternalFrameFactur.setVisible(true);
        jInternalFrameUtilisateur.setVisible(true);
        FiltoJComboUtilisateur();
        jBtnAjoutUtili.setEnabled(false);
        jBtnSuprtUtili.setEnabled(true);
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choisir un chemin");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            //
            // disable the "All files" option.
            //
            chooser.setAcceptAllFileFilterUsed(false);
            //
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                System.out.println("getCurrentDirectory(): "
                        + chooser.getCurrentDirectory());
                System.out.println("getSelectedFile() : "
                        + chooser.getSelectedFile());
                jTextFieldChemin.setText("" + chooser.getSelectedFile());

            } else {
                System.out.println("No Selection ");
            }
        } catch (HeadlessException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    static int nbr = 1;

    private void export(JTable table) {
        if (!"".equals(jTextFieldChemin.getText()) && !"".equals(jTextFieldNomFichier.getText())) {
            FileWriter out = null;
            try {
                String txtr = jTextFieldChemin.getText().replace("\\", "/");
                File outFile1 = new File(txtr + "\\" + jTextFieldNomFichier.getText() + ".xls");
                out = new FileWriter(outFile1);
                jLabel26.setText("Convert ...");
                for (int g = 0; g < table.getColumnCount(); g++) {
                    out.write(table.getColumnName(g) + "\t");
                }
                out.write("\n");
                nbr = table.getRowCount();
                if (nbr == 0 || nbr == 1) {
                    jProgressBar1.setValue(100);
                }

                for (int s = 0; s < table.getRowCount(); s++) {
                    for (int j = 0; j < table.getColumnCount(); j++) {
                        out.write(table.getValueAt(s, j).toString() + "\t");
                    }
                    jProgressBar1.setValue((s * 100 / nbr));
                    out.write("\n");
                }
                jProgressBar1.setValue(100);
                out.close();
                if (jProgressBar1.getValue() == 100) {
                    try {
                        Thread.sleep(800);
                        JOptionPane.showMessageDialog(null, " Félicitation ...");
                        Thread.sleep(600);
                        jDialog2.dispose();
                    } catch (InterruptedException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            export(jtableJournal);
        } catch (Exception e) {
        }


    }//GEN-LAST:event_jButton6ActionPerformed

    private void jComboBoxDestinationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxDestinationActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxDestinationActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        jButtonAjtWilaya.setEnabled(true);
        jButtonSupWilaya.setEnabled(false);
        jTextFieldWilaya.setEnabled(true);
        jComboBoxWilaya.setEnabled(false);
        jLabel30.setText("");
        FiltoJComboWilaya(jComboBoxWilaya, "Wilaya"); 
        jDialog3.setLocationRelativeTo(jInternalFrameFactur);
        jDialog3.setVisible(true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        jButtonAjtWilaya.setEnabled(false);
        jButtonSupWilaya.setEnabled(true);
        jComboBoxWilaya.setEnabled(true);
        jTextFieldWilaya.setEnabled(false);
        jLabel30.setText("");
        FiltoJComboWilaya(jComboBoxWilaya, "Wilaya");
        jDialog3.setLocationRelativeTo(jInternalFrameFactur);
        jDialog3.setVisible(true);
        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void jButtonAjtWilayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAjtWilayaActionPerformed
        try {
            String sqlInsr = "INSERT INTO public.wilaya "
                    + "(nam_w) VALUES (?);";
            pst = c.prepareStatement(sqlInsr);
            pst.setString(1, (String) jTextFieldWilaya.getText());
            pst.executeUpdate();
            pst.close();
            jLabel30.setText("Wilaya de " + jTextFieldWilaya.getText() + " a été ajoutée");
            jTextFieldWilaya.setText("");
            FiltoJComboWilaya(jComboBoxDestination, "Destination");

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "ce produit est existe dans cette facture, si vous decidez de le modifier l'obligation de le supprimer est validée");
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonAjtWilayaActionPerformed

    private void jButtonSupWilayaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSupWilayaActionPerformed
        try {
            String sqlsup = "DELETE FROM public.wilaya WHERE nam_w ='" + (String) jComboBoxWilaya.getSelectedItem() + "'";
            String wly = (String) jComboBoxWilaya.getSelectedItem();
            stmt1.executeUpdate(sqlsup);
            jLabel30.setText("Wilaya de " + wly + " a été supprimé");
            System.out.println(sqlsup);
            FiltoJComboWilaya(jComboBoxDestination, "Destination");
            FiltoJComboWilaya(jComboBoxWilaya, "Wilaya");
            //JOptionPane.showMessageDialog(null, "Wilaya supprimer");
        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonSupWilayaActionPerformed

    private void jRadioButtonMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem1ActionPerformed
        jPanel3.setBackground(new java.awt.Color(255, 255, 204));
        jPanel4.setBackground(new java.awt.Color(255, 255, 204));
        jPanel6.setBackground(new java.awt.Color(255, 255, 204));
        jPanel7.setBackground(new java.awt.Color(255, 255, 204));
    }//GEN-LAST:event_jRadioButtonMenuItem1ActionPerformed

    private void jRadioButtonMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem2ActionPerformed
        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel4.setBackground(new java.awt.Color(204, 255, 255));
        jPanel6.setBackground(new java.awt.Color(204, 255, 255));
        jPanel7.setBackground(new java.awt.Color(204, 255, 255));
    }//GEN-LAST:event_jRadioButtonMenuItem2ActionPerformed

    private void jRadioButtonMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem3ActionPerformed
        jPanel3.setBackground(new java.awt.Color(204, 255, 204));
        jPanel4.setBackground(new java.awt.Color(204, 255, 204));
        jPanel6.setBackground(new java.awt.Color(204, 255, 204));
        jPanel7.setBackground(new java.awt.Color(204, 255, 204));
    }//GEN-LAST:event_jRadioButtonMenuItem3ActionPerformed

    private void jRadioButtonMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem4ActionPerformed
        jPanel3.setBackground(new java.awt.Color(255, 204, 204));
        jPanel4.setBackground(new java.awt.Color(255, 204, 204));
        jPanel6.setBackground(new java.awt.Color(255, 204, 204));
        jPanel7.setBackground(new java.awt.Color(255, 204, 204));
    }//GEN-LAST:event_jRadioButtonMenuItem4ActionPerformed

    private void jRadioButtonMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonMenuItem5ActionPerformed
        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        // TODO add your handling code here:
    }//GEN-LAST:event_jRadioButtonMenuItem5ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        System.exit(0);// TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jTextField5KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField5KeyTyped
        try {
            char e = evt.getKeyChar();

            if ((!(Character.isDigit(e)))
                    && // Only digits
                    (e != '\b')) // For backspace
            {
                evt.consume();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextField5KeyTyped

    private void jTextField15KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField15KeyTyped
        try {
            char e = evt.getKeyChar();
            if ((!(Character.isDigit(e)))
                    && // Only digits
                    (e != '\b')) // For backspace
            {
                evt.consume();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jTextField15KeyTyped

    private void jTextField16KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField16KeyTyped
        try {
            char e = evt.getKeyChar();
            if (e != '.') {
                if ((!(Character.isDigit(e)))
                        && // Only digits
                        (e != '\b')) // For backspace
                {
                    evt.consume();
                }
            }
        } catch (Exception e) {
        }

    }//GEN-LAST:event_jTextField16KeyTyped

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        System.out.println(prefs.get(ID2, "C:\\xxx\\xxx\\FabFacture"));
        txtPathReport.setText("");
        Object[] message = {"Repport", txtPathReport};
        int option = JOptionPane.showConfirmDialog(null, message, "Path", JOptionPane.OK_CANCEL_OPTION);
        if (option == -1) {
            //txtPathReport.setText(prefs.get(ID2, "C:\\xxx\\xxx\\FabFacture\\Report\\FABReport.jrxml"));
        } else {
            if (option == JOptionPane.CANCEL_OPTION) {
                //txtPathReport.setText(prefs.get(ID2, "C:\\xxx\\xxx\\FabFacture\\Report\\FABReport.jrxml"));
            } else {
                prefs.put(ID2, txtPathReport.getText());
            }
        }

    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenu3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu3MouseClicked
//        Help hh = new Help();
//        hh.setVisible(true);
    }//GEN-LAST:event_jMenu3MouseClicked

    private void jComboBoxUtilisateurItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBoxUtilisateurItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxUtilisateurItemStateChanged

    private void jComboBoxUtilisateurActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxUtilisateurActionPerformed
        if (!"Utilisateur".equals((String) jComboBoxUtilisateur.getSelectedItem())) {
            RechercheUtilisateur((String) jComboBoxUtilisateur.getSelectedItem());
        }else{
            jTFUtilisateur.setText("");
            jTFPass.setText("");
            jCheckBox2.setSelected(false);
            jCheckBox3.setSelected(false);
        }
    }//GEN-LAST:event_jComboBoxUtilisateurActionPerformed

    private void jComboClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboClientActionPerformed
        if (jComboClient.getSelectedIndex()==0) {
                jTFnom.setText("");
                jTFadress.setText("");
                jTFactivite.setText("");
                jTFai.setText("");
                jTFnif.setText("");
                jTFrc.setText("");
                jTFdiot.setText("");
                jTFdiot.setText("");
                jTFdateAbn.setText("Aujourd'huit");
        }else{
           try {
            String sql = "SELECT * FROM public.client WHERE nom='"+(String)jComboClient.getSelectedItem()+"'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                jTFnom.setText(reslt.getString("nom"));
                jTFadress.setText(reslt.getString("adress"));
                jTFactivite.setText(reslt.getString("activite"));
                jTFai.setText(reslt.getString("ai"));
                jTFnif.setText(reslt.getString("nif"));
                jTFrc.setText(reslt.getString("rc_cartflah"));
                jTFdiot.setText(reslt.getString("diot"));
                jTFdiot.setText(reslt.getString("diot"));
                jTFdateAbn.setText(formatter.format(reslt.getDate("dateabon")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    }//GEN-LAST:event_jComboClientActionPerformed

    private void jDateChooserDebMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jDateChooserDebMousePressed
//       fieldNamJ.setText("");
//        jTextFieldDiotJrnl.setText("");
//        TableModelJ();
//        jDialog1.setVisible(false);
    }//GEN-LAST:event_jDateChooserDebMousePressed

    private void jTextFieldNumFctKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNumFctKeyReleased
        AfficheFactur(jTextFieldNumFct.getText().trim(), "");
        NamDiot();
        jLabelDel.setVisible(false);
    }//GEN-LAST:event_jTextFieldNumFctKeyReleased

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        jTextFieldNumFct.setEditable(true);
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        jTextFieldNumFct.setEditable(false);
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void jMenuItemRemise0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemRemise0ActionPerformed
        jButton7ActionPerformed(evt);
    }//GEN-LAST:event_jMenuItemRemise0ActionPerformed

    
   
        public static void FiltoJCDes_Um_Mp() {
        try {
            jComboBoxDestination.removeAllItems();
            jComboBoxUM.removeAllItems();
            jComboBoxModeP.removeAllItems();
            //jC.addItem(prr);
//            if (!"".equals(fieldDiot.getText())) {
                String sql = "SELECT Destination, unt_m, mode_paim  FROM public.factur WHERE num_factur = '" + jTextFieldNumFct.getText().trim() + "'";
                    //+ " AND diot = '"+fieldDiot.getText()+"'";
                System.out.println("rani f filtoocombo 9bl excution " + sql);
                reslt = stmt.executeQuery(sql);
                if (reslt.next()) {
                    jComboBoxDestination.addItem(reslt.getString("Destination"));
                    jComboBoxUM.addItem(reslt.getString("unt_m"));
                    jComboBoxModeP.addItem(reslt.getString("mode_paim"));
                    System.out.println("rani f filtoocombo f if dakhel excution " + sql);
                } else {
                    System.out.println("rani f else filtoocombo  ");
                    FiltoJComboWilaya(jComboBoxDestination, "Destination");
                    jComboBoxUM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "U/M", "QUINTAL", "SAC" }));
                    jComboBoxModeP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mode de paiement", "Espece", "Chec", "A terme" }));
                }
//            } else {
//                FiltoJComboWilaya(jComboBoxDestination, "Destination");
//                jComboBoxUM.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"U/M", "QUINTAL", "SAC"}));
//                jComboBoxModeP.setModel(new javax.swing.DefaultComboBoxModel(new String[]{"Mode de paiement", "Espece", "Chec", "A terme"}));
//            }  
            jComboBoxTVA.setSelectedIndex(0);

        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

        
    /*private void FiltoJCDest_Um_Mp(JComboBox jC, String item) {
        try {
            jC.removeAllItems();
            //jC.addItem(prr);
            String sql = "SELECT "+item+" FROM public.factur WHERE num_factur = '" + jTextFieldNumFct.getText() + "'";
            //System.out.println("rani f filtoocombo 9bl excution " + sql);
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                String Ritem = reslt.getString(item);
                jC.addItem(Ritem);
                //System.out.println("rani f filtoocombo avec "+id_client);
            }else{
                 FiltoJComboWilaya(jComboBoxDestination, "Destination");
                 jComboBoxUM.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "U/M", "QUINTAL", "SAC" }));
                 jComboBoxModeP.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Mode de paiement", "Espece", "Chec", "A terme" }));
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    private void FiltoJComboUtilisateur() {
        jComboBoxUtilisateur.removeAllItems();
        jComboBoxUtilisateur.addItem("Utilisateur");
        try {
            String sql = "SELECT nomutil FROM public.utilisateur ";//public.utilisateur(nomutil, pass, read, write, execution)
            reslt = stmt.executeQuery(sql);
            while (reslt.next()) {
                String des_p = reslt.getString("nomutil");
                jComboBoxUtilisateur.addItem(des_p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void FiltoJComboWilaya(JComboBox jc, String ww) {
        jc.removeAllItems();
        jc.addItem(ww);
        try {
            String sql = "SELECT nam_w FROM public.wilaya ";
            reslt = stmt.executeQuery(sql);
            while (reslt.next()) {
                String des_p = reslt.getString("nam_w");
                jc.addItem(des_p);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void FiltoJCombo(JComboBox jC, String prr) {
        jC.removeAllItems();
        jC.addItem(prr);
        try {
            String sql = "SELECT disign_prod FROM public.produit ";
            reslt = stmt.executeQuery(sql);
            while (reslt.next()) {
                String des_p = reslt.getString("disign_prod");
                jC.addItem(des_p);
                // JOptionPane.showMessageDialog(null, num);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void FiltoJCFactur(JComboBox jC, String prr) {
        try {
            jC.removeAllItems();
            jC.addItem(prr);
            String sql = "SELECT num_factur FROM public.factur WHERE diot = '" + fieldDiot.getText() + "' AND date_factur = '" + sqdat() + "' GROUP BY num_factur";
            System.out.println("rani f filtoocombo 9bl excution " + sql);
            reslt = stmt.executeQuery(sql);
            while (reslt.next()) {
                String num_f = reslt.getString("num_factur");
                jC.addItem(num_f);
                //System.out.println("rani f filtoocombo avec "+id_client);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }

        /* }else {
            System.out.println("rani f alse filtoocombo avec "+id_client);
            jC.removeAllItems();
            jC.addItem(prr);
        }*/
    }

    private String diot_f;
    private java.sql.Date date_f;

    private void FactureDeClient() {
        diot_f = null;
        date_f = null;
        try {
            String sql = "SELECT diot, date_factur FROM public.factur WHERE num_factur = '" + jTextFieldNumFct.getText() + "'";// AND date_factur = '"+sqdat()+"'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                date_f = reslt.getDate("date_factur");
                diot_f = reslt.getString("diot");
                //JOptionPane.showMessageDialog(null, "Erreur" + diot_f);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean ProduitDeFacture() {
        boolean b = false;
        try {
            String sql = "SELECT num_prod FROM public.factur WHERE id_facture = '" + idfactr() + "' AND date_factur ='" + sqdat() + "'";
            reslt = stmt.executeQuery(sql);
            if (reslt.next()) {
                b = true;
            }

        } catch (SQLException ex) {
           // JOptionPane.showMessageDialog(null, "Erreur" + ex);
            Logger.getLogger(FabFramFatcture.class.getName()).log(Level.SEVERE, null, ex);
        }
        return b;
    }

    private Float roundOffTo2DecPlaces(float val) {
        return Float.parseFloat(format("%.2f", val));
    }

    private static final String[] dizaineNames = {
        "",
        "",
        "VINGT",
        "TRENTE",
        "QUARANTE",
        "CINQUANTE",
        "SOIXANTE",
        "SOIXANTE",
        "QUATRE-VINGT",
        "QUATRE-VINGT"
    };

    private static final String[] uniteNames1 = {
        "",
        "UN",
        "DEUX",
        "TROIS",
        "QUATRE",
        "CINQ",
        "SIX",
        "SEPT",
        "HUIT",
        "NEUF",
        "DIX",
        "ONZE",
        "DOUZE",
        "TREIZE",
        "QUATORZE",
        "QUINZE",
        "SEIZE",
        "DIX-SEPT",
        "DIX-HUIT",
        "DIX-NEUF"
    };

    private static final String[] uniteNames2 = {
        "",
        "",
        "DEUX",
        "TROIS",
        "QUATRE",
        "CINQ",
        "SIX",
        "SEPT",
        "HUIT",
        "NEUF",
        "DIX"
    };

    private static String convertZeroToHundred(int number) {

        int laDizaine = number / 10;
        int lUnite = number % 10;
        String resultat = "";

        switch (laDizaine) {
            case 1:
            case 7:
            case 9:
                lUnite = lUnite + 10;
                break;
            default:
        }

        // séparateur "-" "et"  ""
        String laLiaison = "";
        if (laDizaine > 1) {
            laLiaison = "-";
        }
        // cas particuliers
        switch (lUnite) {
            case 0:
                laLiaison = "";
                break;
            case 1:
                if (laDizaine == 8) {
                    laLiaison = "-";
                } else {
                    laLiaison = " ET ";
                }
                break;
            case 11:
                if (laDizaine == 7) {
                    laLiaison = " ET ";
                }
                break;
            default:
        }

        // dizaines en lettres
        switch (laDizaine) {
            case 0:
                resultat = uniteNames1[lUnite];
                break;
            case 8:
                if (lUnite == 0) {
                    resultat = dizaineNames[laDizaine];
                } else {
                    resultat = dizaineNames[laDizaine]
                            + laLiaison + uniteNames1[lUnite];
                }
                break;
            default:
                resultat = dizaineNames[laDizaine]
                        + laLiaison + uniteNames1[lUnite];
        }
        return resultat;
    }

    private static String convertLessThanOneThousand(int number) {

        int lesCentaines = number / 100;
        int leReste = number % 100;
        String sReste = convertZeroToHundred(leReste);

        String resultat;
        switch (lesCentaines) {
            case 0:
                resultat = sReste;
                break;
            case 1:
                if (leReste > 0) {
                    resultat = "CENT " + sReste;
                } else {
                    resultat = "CENT";
                }
                break;
            default:
                if (leReste > 0) {
                    resultat = uniteNames2[lesCentaines] + " CENT " + sReste;
                } else {
                    resultat = uniteNames2[lesCentaines] + " CENTS";
                }
        }
        return resultat;
    }

    // pad des "0"
    private final String mask = "000000000000";
    private final DecimalFormat df = new DecimalFormat(mask);

    //hada kan long derto double
    private String convert(double number) {
        // 0 à 999 999 999 999
        if (number == 0) {
            return "ZERO";
        }

        //String snumber = Double.toString(number);
        String snumber = df.format(number);

        // XXXnnnnnnnnn
        int lesMilliards = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int lesMillions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int lesCentMille = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int lesMille = Integer.parseInt(snumber.substring(9, 12));

        String tradMilliards;
        switch (lesMilliards) {
            case 0:
                tradMilliards = "";
                break;
            case 1:
                tradMilliards = convertLessThanOneThousand(lesMilliards)
                        + " MILLIARD ";
                break;
            default:
                tradMilliards = convertLessThanOneThousand(lesMilliards)
                        + " MILLIARDS ";
        }
        String resultat = tradMilliards;

        String tradMillions;
        switch (lesMillions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(lesMillions)
                        + " MILLION ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(lesMillions)
                        + " MILLIONS ";
        }
        resultat = resultat + tradMillions;

        String tradCentMille;
        switch (lesCentMille) {
            case 0:
                tradCentMille = "";
                break;
            case 1:
                tradCentMille = "MILLE ";
                break;
            default:
                tradCentMille = convertLessThanOneThousand(lesCentMille)
                        + " MILLES ";
        }
        resultat = resultat + tradCentMille;

        String tradMille;
        tradMille = convertLessThanOneThousand(lesMille);
        resultat = resultat + tradMille;

        return resultat;
    }

    /**
     * @param args the command line arguments
     */
    public static final FabFramFatcture fenster = new FabFramFatcture();
//
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
//            e.printStackTrace();
//        }
//        SwingUtilities.updateComponentTreeUI(fenster);
//        //fenster.pack();
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            fenster.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//            fenster.setVisible(true);
//        });
//
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jBtnAjoutUtili;
    private javax.swing.JButton jBtnSuprtUtili;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButtonAjtWilaya;
    private javax.swing.JButton jButtonSupWilaya;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    public static javax.swing.JComboBox jComboBoxDestination;
    public static javax.swing.JComboBox jComboBoxModeP;
    public static javax.swing.JComboBox jComboBoxTVA;
    public static javax.swing.JComboBox jComboBoxUM;
    private javax.swing.JComboBox<String> jComboBoxUtilisateur;
    private javax.swing.JComboBox<String> jComboBoxWilaya;
    private javax.swing.JComboBox<String> jComboClient;
    public static javax.swing.JComboBox jComboboxProduit;
    private javax.swing.JComboBox jComboboxProduit1;
    private javax.swing.JComboBox jComboboxProduit2;
    public static com.toedter.calendar.JDateChooser jDateChooser1;
    public static com.toedter.calendar.JDateChooser jDateChooserDeb;
    public static com.toedter.calendar.JDateChooser jDateChooserFin;
    public static javax.swing.JDialog jDialog1;
    private javax.swing.JDialog jDialog2;
    private javax.swing.JDialog jDialog3;
    public javax.swing.JInternalFrame jInternalFrameCalculatrice;
    public javax.swing.JInternalFrame jInternalFrameClient;
    public static javax.swing.JInternalFrame jInternalFrameFactur;
    public static javax.swing.JInternalFrame jInternalFrameJournal;
    public javax.swing.JInternalFrame jInternalFrameProduit;
    public javax.swing.JInternalFrame jInternalFrameUtilisateur;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel1Excel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabelDel;
    public static javax.swing.JLabel jLabelRapport;
    public static javax.swing.JLabel jLabelRemarque;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu6;
    private javax.swing.JMenuBar jMenuBar1;
    public javax.swing.JMenu jMenuClient;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    public javax.swing.JMenuItem jMenuItemCalcul;
    public javax.swing.JMenuItem jMenuItemFacture;
    public javax.swing.JMenuItem jMenuItemJournal;
    public javax.swing.JMenuItem jMenuItemProduit;
    public javax.swing.JMenuItem jMenuItemRemise0;
    public javax.swing.JMenu jMenuRepport;
    private javax.swing.JMenu jMenuTheme;
    public javax.swing.JMenu jMenuUnlockNum;
    public javax.swing.JMenu jMenuUnlockPrec;
    public javax.swing.JMenu jMenuUtilisateur;
    public javax.swing.JMenu jMenuWilaya;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    public javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    public javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    final javax.swing.JProgressBar jProgressBar1 = new javax.swing.JProgressBar();
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem2;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem3;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem4;
    private javax.swing.JRadioButtonMenuItem jRadioButtonMenuItem5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JTextField jTFPass;
    private javax.swing.JTextField jTFUtilisateur;
    private javax.swing.JTextField jTFactivite;
    private javax.swing.JTextField jTFadress;
    private javax.swing.JTextField jTFai;
    private javax.swing.JTextField jTFdateAbn;
    private javax.swing.JTextField jTFdiot;
    private javax.swing.JTextField jTFnif;
    private javax.swing.JTextField jTFnom;
    private javax.swing.JTextField jTFrc;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTable jTable1;
    public static javax.swing.JTable jTableDetail;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField5;
    static javax.swing.JTextField jTextFieldChemin;
    static javax.swing.JTextField jTextFieldNomFichier;
    public static javax.swing.JTextField jTextFieldNumFct;
    private javax.swing.JTextField jTextFieldQtt;
    private javax.swing.JTextField jTextFieldReduction;
    private javax.swing.JTextField jTextFieldWilaya;
    public static javax.swing.JTable jtableFactur;
    public static javax.swing.JTable jtableJournal;
    private javax.swing.JPopupMenu menuC;
    private javax.swing.JPanel panelC;
    // End of variables declaration//GEN-END:variables
}