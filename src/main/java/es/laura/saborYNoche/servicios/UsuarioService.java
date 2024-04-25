package es.laura.saborYNoche.servicios;

import es.laura.saborYNoche.dto.UsuarioLoginDTO;
import es.laura.saborYNoche.dto.UsuarioDTO;

public interface UsuarioService  {
    public UsuarioDTO login(UsuarioLoginDTO usuarioLoginDTO);

    public UsuarioDTO crear(UsuarioDTO usuarioDTO) throws Exception;


}
