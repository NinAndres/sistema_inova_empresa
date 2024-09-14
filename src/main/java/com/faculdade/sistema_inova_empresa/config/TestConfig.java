package com.faculdade.sistema_inova_empresa.config;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.faculdade.sistema_inova_empresa.entities.Eventos;
import com.faculdade.sistema_inova_empresa.entities.Ideias;
import com.faculdade.sistema_inova_empresa.entities.Usuario;
import com.faculdade.sistema_inova_empresa.entities.enums.ImpactoStatus;
import com.faculdade.sistema_inova_empresa.entities.enums.RoleStatus;
import com.faculdade.sistema_inova_empresa.repositories.EventosRepository;
import com.faculdade.sistema_inova_empresa.repositories.IdeiasRepository;
import com.faculdade.sistema_inova_empresa.repositories.UsuarioRepository;

@Component
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EventosRepository eventosRepository;

    @Autowired
    private IdeiasRepository ideiasRepository;

    @Override
    public void run(String... args) throws Exception {
        Usuario admin = new Usuario();
        admin.setNome("Nicolas");
        admin.setEmail("nicolas@faculdade.com");
        admin.setSenha("admin123");
        admin.setRole(RoleStatus.ADMIN);
        usuarioRepository.save(admin);

        Usuario jurado1 = new Usuario();
        jurado1.setNome("Jurado 1");
        jurado1.setEmail("jurado1@faculdade.com");
        jurado1.setSenha("jurado123");
        jurado1.setRole(RoleStatus.JURADO);
        usuarioRepository.save(jurado1);

        Usuario jurado2 = new Usuario();
        jurado2.setNome("Jurado 2");
        jurado2.setEmail("jurado2@faculdade.com");
        jurado2.setSenha("jurado123");
        jurado2.setRole(RoleStatus.JURADO);
        usuarioRepository.save(jurado2);

        Eventos evento = new Eventos();
        evento.setNome("Evento Inovação 2024");
        evento.setDescricao("Evento para apresentação de ideias inovadoras");
        evento.setDataInicio(LocalDateTime.now().minusDays(1));
        evento.setDataFim(LocalDateTime.now().plusDays(7));
        evento.setCriador(admin);
        evento.setJurados(Arrays.asList(jurado1, jurado2));
        eventosRepository.save(evento);

        Ideias ideia1 = new Ideias();
        ideia1.setNome("Plataforma de Gestão de Resíduos");
        ideia1.setDescricao(
                "Uma plataforma digital que ajuda empresas a gerenciarem resíduos de forma eficiente, com foco em sustentabilidade.");
        ideia1.setEvento(evento);
        ideia1.setCriador(admin);
        ideia1.setImpacto(ImpactoStatus.MEDIO);
        ideiasRepository.save(ideia1);

        Ideias ideia2 = new Ideias();
        ideia2.setNome("Aplicativo de Saúde Mental");
        ideia2.setDescricao(
                "App que oferece suporte psicológico e dicas de bem-estar com profissionais disponíveis para consultas virtuais.");
        ideia2.setEvento(evento);
        ideia2.setCriador(admin);
        ideia2.setCusto(399.999F);
        ideia2.setImpacto(ImpactoStatus.ALTO);
        ideiasRepository.save(ideia2);

        Ideias ideia3 = new Ideias();
        ideia3.setNome("Sistema de Energia Solar Inteligente");
        ideia3.setDescricao(
                "Um sistema que monitora o uso de energia solar e ajusta automaticamente o consumo para otimizar a eficiência.");
        ideia3.setEvento(evento);
        ideia3.setCriador(admin);
        ideia3.setCusto(200.00F);
        ideia3.setImpacto(ImpactoStatus.BAIXO);
        ideiasRepository.save(ideia3);
    }
}
