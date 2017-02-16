/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilidades;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Luigie
 */
public class crearcarpeta {

    public File Ruta() {
        String clase = crearcarpeta.class.getSimpleName() + ".class";
        URL rutaca = crearcarpeta.class.getResource(clase); // traigo dirreccion
        File ruta = null;
        try {
            if (rutaca.getProtocol().equals("file")) {

                File f = new File(rutaca.toURI());
                f = f.getParentFile();
                f = f.getParentFile();
                f = f.getParentFile();
                f = f.getParentFile();
//                f = f.getParentFile();

                ruta = f;

            } else if (rutaca.getProtocol().equals("jar")) {
                String expected = "!/" + clase;
                String s = rutaca.toString();
                s = s.substring(5);
                s = s.substring(0, s.length() - expected.length());
                File f;
                f = new File(new URL(s).toURI());

                do {
                    f = f.getParentFile();
                    f = f.getParentFile();
                    f = f.getParentFile();
                    f = f.getParentFile();
//                    f = f.getParentFile();

                    ruta = f;
                } while (!f.isDirectory());
            }

        } catch (Exception ex) {
            Logger.getLogger(crearcarpeta.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ruta;
    }

    public int totalArchivos(File directorio) {
        crearcarpeta rutas = new crearcarpeta();
        int total = 0;
        String[] arrArchivos = directorio.list();
        total += arrArchivos.length;
        File tmpFile;
        for (int i = 0; i < arrArchivos.length; ++i) {
            tmpFile = new File(directorio.getPath() + "/" + arrArchivos[i]);
            if (tmpFile.isDirectory()) {
                total += totalArchivos(tmpFile);
            }
        }

        return total;
    }

    public void EliminarArchivosTemp(File directorio, String imgcargadas) {
        crearcarpeta rutas = new crearcarpeta();
        int total = 0;
        String[] arrArchivos = directorio.list();
        total += arrArchivos.length;
        File tmpFile;
        for (int i = 0; i < arrArchivos.length; ++i) {
            tmpFile = new File(directorio.getPath() + "/" + arrArchivos[i]);
            if (tmpFile.getName().equals("default.png") || tmpFile.getName().equals("default3.png") || tmpFile.getName().equals("default2.png")) {
            } else if (tmpFile.getName().equals(imgcargadas+".png")) {
                tmpFile.delete();
            }
        }
        imgcargadas = "";
    }
    public void EliminarArchivosTempLista(File directorio, List<Map> imgcargadas) {
        crearcarpeta rutas = new crearcarpeta();
        int total = 0;
        String[] arrArchivos = directorio.list();
        total += arrArchivos.length;
        File tmpFile;
        for (int i = 0; i < arrArchivos.length; ++i) {
            tmpFile = new File(directorio.getPath() + "/" + arrArchivos[i]);
            if (tmpFile.getName().equals("default.png") || tmpFile.getName().equals("default3.png") || tmpFile.getName().equals("default2.png")) {
            }else {
                for (Map img : imgcargadas) {
                    if (tmpFile.getName().equals(img.get("nombre")+".png")) {
                        tmpFile.delete();
                    }
                }
            }
        }
        imgcargadas.clear();
    }

    public static void eliminarPorExtension(String path, final String extension) {
        File[] archivos = new File(path).listFiles(new FileFilter() {
            public boolean accept(File archivo) {
                if (archivo.isFile()) {
                    return archivo.getName().endsWith('.' + extension);
                }
                return false;
            }
        });
        for (File archivo : archivos) {
            archivo.delete();
        }
    }

}
