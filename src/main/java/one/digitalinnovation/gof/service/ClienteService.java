package one.digitalinnovation.gof.service;

import one.digitalinnovation.gof.model.Cliente;

<<<<<<< HEAD
/**
 * Interface que define o padrão <b>Strategy</b> no domínio de cliente. Com
 * isso, se necessário, podemos ter multiplas implementações dessa mesma
 * interface.
 * 
 * @author michelabs
 */

public interface ClienteService {

=======
public interface ClienteService {
	
>>>>>>> 70afef37fe8d4f169b0fbf5b97688dc098a666da
	Iterable<Cliente> buscarTodos();
	Cliente buscarPorId(Long id);
	void inserir(Cliente cliente);
	void atualizar(Long id, Cliente cliente);
	void deletar(Long id);
<<<<<<< HEAD

}
=======
}
>>>>>>> 70afef37fe8d4f169b0fbf5b97688dc098a666da
