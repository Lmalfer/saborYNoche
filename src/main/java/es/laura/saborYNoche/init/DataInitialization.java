//package es.laura.saborYNoche.init;
//
//import es.laura.saborYNoche.model.Categoria;
//import es.laura.saborYNoche.model.TipoEstablecimiento;
//import es.laura.saborYNoche.repository.CategoriaRepository;
//import es.laura.saborYNoche.repository.TipoEstablecimientoRepository;
//import jakarta.annotation.PostConstruct;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class DataInitialization {
//
//    @Autowired
//    private CategoriaRepository categoriaRepository;
//
//    @Autowired
//    private TipoEstablecimientoRepository tipoEstablecimientoRepository;
//
//    @PostConstruct
//    public void initData() {
//        // Cargar categorías
//        Categoria acogedorIntimo = new Categoria();
//        acogedorIntimo.setNombre("Acogedor e íntimo");
//        categoriaRepository.save(acogedorIntimo);
//
//        Categoria conVistas = new Categoria();
//        conVistas.setNombre("Con vistas");
//        categoriaRepository.save(conVistas);
//
//        Categoria familiar = new Categoria();
//        familiar.setNombre("Familiar");
//        categoriaRepository.save(familiar);
//
//        Categoria amigos = new Categoria();
//        amigos.setNombre("Amigos");
//        categoriaRepository.save(amigos);
//
//        // Cargar tipos de establecimiento
//        TipoEstablecimiento bar = new TipoEstablecimiento();
//        bar.setNombre("Bar");
//        tipoEstablecimientoRepository.save(bar);
//
//        TipoEstablecimiento restaurante = new TipoEstablecimiento();
//        restaurante.setNombre("Restaurante");
//        tipoEstablecimientoRepository.save(restaurante);
//
//        TipoEstablecimiento pub = new TipoEstablecimiento();
//        pub.setNombre("Pub");
//        tipoEstablecimientoRepository.save(pub);
//
//        TipoEstablecimiento discoteca = new TipoEstablecimiento();
//        discoteca.setNombre("Discoteca");
//        tipoEstablecimientoRepository.save(discoteca);
//    }
//}
