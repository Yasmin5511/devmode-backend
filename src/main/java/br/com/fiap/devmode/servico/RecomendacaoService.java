package br.com.fiap.devmode.servico;

import br.com.fiap.devmode.modelo.AcaoSustentavel;
import br.com.fiap.devmode.modelo.Categoria;
import br.com.fiap.devmode.modelo.ConteudoSustentavel;
import br.com.fiap.devmode.modelo.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecomendacaoService {

    public Map<String, Object> recomendar(Integer idUsuario) {

        UsuarioService usuarioService = new UsuarioService();
        AcaoSustentavelService acaoService = new AcaoSustentavelService();
        ConteudoSustentavelService conteudoService = new ConteudoSustentavelService();

        Usuario usuario = usuarioService.buscarPorId(idUsuario);

        if (usuario == null) {
            return null;
        }

        List<Categoria> categorias = usuarioService.buscarCategorias(idUsuario);

        for (Categoria categoria : categorias) {
            usuario.escolherCategoria(categoria);
        }

        List<AcaoSustentavel> acoes = acaoService.listarTodas();
        List<ConteudoSustentavel> conteudos = conteudoService.listarTodos();

        SistemaRecomendacao sistema = new SistemaRecomendacao();

        for (AcaoSustentavel acao : acoes) {
            sistema.adicionarAcao(acao);
        }

        for (ConteudoSustentavel conteudo : conteudos) {
            sistema.adicionarConteudo(conteudo);
        }

        List<AcaoSustentavel> acoesRecomendadas = sistema.recomendarAcoes(usuario);

        List<ConteudoSustentavel> conteudosRecomendados = sistema.recomendarConteudos(usuario);

        Map<String, Object> resultado = new HashMap<>();

        resultado.put("acoes", acoesRecomendadas);
        resultado.put("conteudos", conteudosRecomendados);

        return resultado;
    }
}
