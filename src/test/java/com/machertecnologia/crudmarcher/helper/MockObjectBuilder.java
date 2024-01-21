package com.machertecnologia.crudmarcher.helper;

import com.machertecnologia.crudmarcher.usuario.DTO.AtualizacaoUsuarioDTO;
import com.machertecnologia.crudmarcher.usuario.adapter.CredencialUsuarioPort;
import com.machertecnologia.crudmarcher.usuario.adapter.UsuarioPort;
import com.machertecnologia.crudmarcher.usuario.model.*;

import com.machertecnologia.crudmarcher.usuario.service.AutorizacaoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.mockito.Mockito.mock;

@Component
public class MockObjectBuilder {

    private static final Random random = new Random();

    public static Usuario buildMockUsuario() {
        Endereco mockEndereco = buildMockEndereco();
        CredencialUsuario mockCredencialUsuario = buildMockCredencialUsuario();

        Usuario usuario = new Usuario();
        usuario.setId(random.nextLong());
        usuario.setCpf(generateRandomCpf());
        usuario.setNome(generateRandomName());
        usuario.setDataNascimento(generateRandomBirthDate());
        usuario.setEndereco(mockEndereco);
        usuario.setStatus(StatusRegistro.ATIVO);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setUsuarioCriacao(generateRandomUsername());
        usuario.setCredencialUsuario(mockCredencialUsuario);

        return usuario;
    }

    public static Usuario buildMockUsuario(Long id) {
        Endereco mockEndereco = buildMockEndereco();
        CredencialUsuario mockCredencialUsuario = buildMockCredencialUsuario();

        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setCpf(generateRandomCpf());
        usuario.setNome(generateRandomName());
        usuario.setDataNascimento(generateRandomBirthDate());
        usuario.setEndereco(mockEndereco);
        usuario.setStatus(StatusRegistro.ATIVO);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setUsuarioCriacao(generateRandomUsername());
        usuario.setCredencialUsuario(mockCredencialUsuario);

        return usuario;
    }

    public static Usuario buildMockUsuario(CredencialUsuario credencialUsuario) {
        Endereco mockEndereco = buildMockEndereco();
        CredencialUsuario mockCredencialUsuario = buildMockCredencialUsuario();

        Usuario usuario = new Usuario();
        usuario.setId(random.nextLong());
        usuario.setCpf(generateRandomCpf());
        usuario.setNome(generateRandomName());
        usuario.setDataNascimento(generateRandomBirthDate());
        usuario.setEndereco(mockEndereco);
        usuario.setStatus(StatusRegistro.ATIVO);
        usuario.setDataCriacao(LocalDateTime.now());
        usuario.setUsuarioCriacao(generateRandomUsername());
        usuario.setCredencialUsuario(credencialUsuario);

        return usuario;
    }

    private static Endereco buildMockEndereco() {
        Endereco endereco = new Endereco();
        endereco.setRua(generateRandomString());
        endereco.setNumero(Integer.toString(random.nextInt(100)));
        endereco.setComplemento(generateRandomString());
        endereco.setBairro(generateRandomString());
        endereco.setCidade(generateRandomString());
        endereco.setEstado(generateRandomString());
        endereco.setCep(generateRandomString());

        return endereco;
    }

    private static CredencialUsuario buildMockCredencialUsuario() {
        CredencialUsuario credencialUsuario = new CredencialUsuario();
        credencialUsuario.setLogin(generateRandomUsername());
        credencialUsuario.setPassword(generateRandomString());
        credencialUsuario.setRole(RoleUsuario.USER);

        return credencialUsuario;
    }

    public static AtualizacaoUsuarioDTO buildAtualizacaoUsuarioDTO(String password, RoleUsuario role) {
        return new AtualizacaoUsuarioDTO(
                generateRandomCpf(),
                generateRandomName(),
                LocalDate.now().minusYears(random.nextInt(33) + 18),
                buildMockEndereco(),
                generateRandomUsername(),
                password,
                role
        );
    }

    public static AtualizacaoUsuarioDTO buildAtualizacaoUsuarioDTO(String login, String password, RoleUsuario role) {
        return new AtualizacaoUsuarioDTO(
                generateRandomCpf(),
                generateRandomName(),
                LocalDate.now().minusYears(random.nextInt(33) + 18),
                buildMockEndereco(),
                login,
                password,
                role
        );
    }

    public static Optional<Usuario> buildOptionalUsuario(Long id) {
        return Optional.of(buildMockUsuario());
    }

    public static UsuarioPort buildMockUsuarioRepositoryPort() {
        return mock(UsuarioPort.class);
    }

    public static AutorizacaoService buildMockAutorizacaoService() {
        return mock(AutorizacaoService.class);
    }

    public static CredencialUsuarioPort buildMockCredencialUsuarioRepositoryPort() {
        return mock(CredencialUsuarioPort.class);
    }

    public static Authentication buildMockAuthentication() {
        return mock(Authentication.class);
    }

    private static String generateRandomString() {
        int length = 10;
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            result.append(characters.charAt(random.nextInt(characters.length())));
        }

        return result.toString();
    }

    private static String generateRandomName() {
        String[] names = {"Alice", "Bob", "Charlie", "David", "Eva", "Frank", "Grace", "Hank", "Ivy", "Jack"};
        return names[random.nextInt(names.length)];
    }

    private static String generateRandomUsername() {
        return generateRandomString().toLowerCase();
    }

    private static String generateRandomCpf() {
        // Geração de um CPF aleatório simples para fins ilustrativos
        return String.format("%03d.%03d.%03d-%02d",
                random.nextInt(1000),
                random.nextInt(1000),
                random.nextInt(1000),
                random.nextInt(100));
    }

    private static LocalDate generateRandomBirthDate() {
        long minDay = LocalDate.of(1950, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2000, 1, 1).toEpochDay();
        long randomDay = minDay + random.nextLong() % (maxDay - minDay);

        return LocalDate.ofEpochDay(randomDay);
    }
}