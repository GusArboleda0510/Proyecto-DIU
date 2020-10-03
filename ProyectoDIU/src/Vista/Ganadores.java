/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vista;

import Control.ControlTXT;
import Modelo.CreadordeDocs;
import Modelo.LectordeDocs;
import java.io.File;
import java.util.ArrayList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Alejandra Becerra
 */
public class Ganadores extends javax.swing.JDialog {

    /**
     * Creates new form Ganadores
     */
    CreadordeDocs doc;
    LectordeDocs ld;
    String tiempo, puntaje,nickName="ALEJANDRA";
    File ganadores = new File("persistencia/Ganadores.xml");
    NodeList lista;
    Thread imagenes;
    
    ControlTXT txt = new ControlTXT();
    
    public Ganadores(java.awt.Frame parent, boolean modal) throws Exception {
        super(parent, modal);
        initComponents();
        
        doc = new CreadordeDocs(ganadores, "ganadores");
        ganadores();
        setVisible(true);
    }
    
    public void ganadores() throws Exception{
        
        String[] dato=txt.leerTodo();
        tiempo=dato[0];
        puntaje=dato[2];
//        nickName=dato[3];//Agregar al TXT(NickName)

        generarContenido(doc.getDocumento(),doc.getElementoRaiz());///Añade al XML
        obtenerContenido();
        
    }
    
    private void generarContenido(Document documento, Element raiz) throws Exception{
        Element puestos = documento.createElement("puestos");
        raiz.appendChild(puestos);
        
        Element jugador = documento.createElement("jugadores");
        jugador.appendChild(documento.createTextNode(nickName));
        puestos.appendChild(jugador);       
        
                
        Element puntajeJugador = documento.createElement("puntaje");
        puntajeJugador.appendChild(documento.createTextNode(puntaje));
        puestos.appendChild(puntajeJugador);             
        
        Element timepo = documento.createElement("timepo");
        timepo.appendChild(documento.createTextNode(tiempo));
        puestos.appendChild(timepo);
        doc.generarXML(ganadores, documento);
    }
    public void obtenerContenido() throws Exception {
        
        ld = new LectordeDocs(ganadores);
        Document documento = ld.getDocumento();
        lista = documento.getElementsByTagName("puestos");
        
        String[] datos= new String[lista.getLength()];
        
        for (int n = 0; n < lista.getLength(); n++) {
            Node nodo = lista.item(n);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodo;
                datos[n]=element.getElementsByTagName("jugadores").item(0).getTextContent()
                        +"-"+element.getElementsByTagName("puntaje").item(0).getTextContent()
                        +"-"+element.getElementsByTagName("timepo").item(0).getTextContent();         
            }
        }
        ordenarXML(datos);
    }
    public void ordenarXML(String[] datos){
        String[] aux=null;
        int[] puntaje=new int[datos.length];
        int[] temporal=new int[datos.length];
        
        for (int i = 0; i < datos.length; i++) {
            aux=datos[i].split("-");
            puntaje[i]=Integer.parseInt(aux[1]);
            temporal[i]=Integer.parseInt(aux[1]);
        }
        int[] posiciones=ordenamiento(puntaje, mayorMenos(temporal));
        
        for (int i = 0; i < datos.length; i++) {
            jTextArea1.append(""+(i+1)+"   ");
            String[] aux01=datos[posiciones[i]].split("-");
            for (int j = 0; j < aux01.length; j++) {
            
            jTextArea1.append(" "+aux01[j] +"   ");
                
            }
            jTextArea1.append("\n");
        }
    }
    public int[] ordenamiento (int[] p , int[]ordendado){
        int[]posiciones = new int[p.length];
                for (int i = 0; i < p.length; i++) {
            for (int j = 0; j < p.length; j++) {
                if(ordendado[i] == p[j]){
                    posiciones[i]=j;
                    break;
                }
            }
        }
                return posiciones;
    }
    public int[] mayorMenos(int[] a) {  
    int[] aux=null;
    int menor;
    for (int x = 0; x < a.length; x++) {
        for (int i = 0; i < a.length-x-1; i++) {
            if(a[i] < a[i+1]){
                int tmp = a[i+1];
                a[i+1] = a[i];
                a[i] = tmp;
            }
        }
    }
    return aux=a;
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();
        Trofeo = new javax.swing.JLabel();
        Trofeo1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 90)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GANADORES");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 21, 980, 90));

        jTextArea1.setColumns(5);
        jTextArea1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 18)); // NOI18N
        jTextArea1.setRows(5);
        jTextArea1.setBorder(new javax.swing.border.MatteBorder(null));
        jScrollPane1.setViewportView(jTextArea1);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(235, 129, 504, 380));

        jButton1.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/salir.png"))); // NOI18N
        jButton1.setText("SALIR");
        jButton1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jButton1.setContentAreaFilled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 540, 500, 52));

        Trofeo.setIcon(new javax.swing.ImageIcon("D:\\Alejandra\\Documentos\\NetBeansProjects1\\DIU\\Proyecto-DIU\\ProyectoDIU\\src\\Imagenes\\trofeo-girando-16777.gif")); // NOI18N
        jPanel1.add(Trofeo, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 254, 160, 160));

        Trofeo1.setIcon(new javax.swing.ImageIcon("D:\\Alejandra\\Documentos\\NetBeansProjects1\\DIU\\Proyecto-DIU\\ProyectoDIU\\src\\Imagenes\\trofeo-girando-16777.gif")); // NOI18N
        jPanel1.add(Trofeo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(786, 237, 160, 160));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/fondoBlanco.jpg"))); // NOI18N
        jLabel2.setText("jLabel2");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 650));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        setSize(new java.awt.Dimension(1014, 672));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String[] args) throws Exception {
        new Ganadores(null, true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Trofeo;
    private javax.swing.JLabel Trofeo1;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
