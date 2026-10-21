package com.example.Floristeria;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller 
public class ProductoController {
    private final List <String> categorias = List.of ("Ramos", "Arreglos", "Plantas");
    private final List <String> imagenes = List.of ("rosa.jpg", "girasol.jpg", "orquidea.jpg");
    private final List <Producto> productos = new CopyOnWriteArrayList<>(List.of(new Producto("Ramo de rosas", "Ramo", new BigDecimal("1500"), "rosa.jpg"), new Producto("Arreglo de girasoles", "Arreglos", new BigDecimal("18000"), "girasol.jpg"), new Producto("Orquidea en maceta", "Plantas", new BigDecimal("12500"), "orquidea.jpg")));

    @GetMapping ("/")
    public String mostrarCatalogo(
        @RequestParam (name = "buscar", defaultValue= "")
        String buscar,
        @RequestParam  (name = "categoria", defaultValue= "")
        String categoria,
        Model model){
            String texto = buscar.strip().toLowerCase(Locale.ROOT);
            List<Producto> resultado = productos.stream().filter(p-> p.getNombre().toLowerCase(Locale.ROOT).contains(texto)).filter(p-> categoria.isBlank() || p.getCategoria().equals(categoria)).toList();
            model.addAttribute("productos", resultado);
            model.addAttribute("categorias", categorias);
            model.addAttribute("imagenes", imagenes);
            model.addAttribute("buscar", buscar);
            model.addAttribute("categoriaActual", categoria);
            return "index";

        }

        @PostMapping ("/productos")
        public String agregarProducto(
            @RequestParam (name = "nombre")String nombre,
            @RequestParam  (name = "categoria")String categoria,
            @RequestParam  (name = "precio")String precio,
            @RequestParam  (name = "imagen")String imagen,
            RedirectAttributes mensajes){
                String nombreLimpio = nombre.strip();
                BigDecimal precioNumerico;
                try {
                    precioNumerico = new BigDecimal(precio.strip());
                } catch (NumberFormatException e) {
                    mensajes.addFlashAttribute("error", "Escriba un precio númerico válido");
                    return "redirect:/";
                }
                if(nombreLimpio.isBlank()|| precioNumerico.compareTo(BigDecimal.ZERO) <= 0 || precioNumerico.scale()> 2 || !categorias.contains(imagen)){mensajes.addFlashAttribute("error", "Revise los datos: nombre válido, precio positivo" + "con hasta dos decimales y opciones permitidas");
                    return "redirect:/";

                }
                productos.add(new Producto(nombreLimpio, categoria, precioNumerico, imagen));
                mensajes.addFlashAttribute("mensaje", "Producto agregado correctamente.");
                return "redirect:/";
            }
    
}

