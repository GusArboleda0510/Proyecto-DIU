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
    
    public Ganadores(java.awt.Frame parent, boolean modal) throws Exception {
        super(parent, modal);
        initComponents();
        
        doc = new CreadordeDocs(ganadores, "ganadores");
        ganadores();
        setVisible(true);
    }
    
    public void ganadores() throws Exception{
        ControlTXT txt = new ControlTXT();
        String[] dato=txt.leerTXT();
        tiempo=dato[0];
        puntaje=dato[2];
//        nickName=dato[3];
//        generarContenido(doc.getDocumento(),doc.getElementoRaiz());
        obtenerContenido();//Falta obtener contenido para mostrarlo por la interfaz
        
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
        System.out.println(lista.getLength()+"....");
        String[] datos= new String[lista.getLength()];
//        datos = new String[lista.getLength()];
        System.out.println("Ganadanores");
        for (int n = 0; n < lista.getLength(); n++) {
            Node nodo = lista.item(n);
            if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nodo;
//                System.out.println(element.getElementsByTagName("jugadores").item(0).getTextContent());
//                System.out.println(element.getElementsByTagName("puntaje").item(0).getTextContent());
//                System.out.println(element.getElementsByTagName("timepo").item(0).getTextContent());
                datos[n]=element.getElementsByTagName("jugadores").item(0).getTextContent()
                        +"-"+element.getElementsByTagName("puntaje").item(0).getTextContent()
                        +"-"+element.getElementsByTagName("timepo").item(0).getTextContent();
//                
            }
            
        }
        for (int i = 0; i < datos.length; i++) {
//                System.out.println(datos[i]);
                
            }
        ordenarXML(datos);
    }
    public void ordenarXML(String[] datos){
        String[] aux=null;
        int[] puntaje=new int[datos.length];
        int[] temporal=new int[datos.length];
        
        for (int i = 0; i < datos.length; i++) {
            System.out.println(datos[i]);
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
        System.out.println(" ");
        int[]posiciones = new int[p.length];
                for (int i = 0; i < p.length; i++) {
//            System.out.print(p[i]+" -- ");

            for (int j = 0; j < p.length; j++) {
                    System.out.println("preuba  "+ ordendado[i] + "== " +"p " + p[j]);
                if(ordendado[i] == p[j]){
//                    System.out.println("-- " + datos[i]+"--");
                    System.out.println(j);
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
    System.out.println("ORDENADO");
    for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]+"-");
//            
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 60)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("GANADORES");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Tempus Sans ITC", 3, 14)); // NOI18N
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jButton1.setFont(new java.awt.Font("Tempus Sans ITC", 1, 24)); // NOI18N
        jButton1.setText("SALIR");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(291, 291, 291)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(237, 237, 237)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(257, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        setSize(new java.awt.Dimension(1014, 690));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String[] args) throws Exception {
        new Ganadores(null, true);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
