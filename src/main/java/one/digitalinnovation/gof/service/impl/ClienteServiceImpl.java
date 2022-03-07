package one.digitalinnovation.gof.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.digitalinnovation.gof.model.Cliente;
import one.digitalinnovation.gof.model.ClienteRepository;
import one.digitalinnovation.gof.model.Endereco;
import one.digitalinnovation.gof.model.EnderecoRepository;
import one.digitalinnovation.gof.service.ClienteService;
import one.digitalinnovation.gof.service.ViaCepService;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	//Singleton: Injetar os componentes do Spring com @Autowired.
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private ViaCepService viaCepService;
	
	//Strategy: Implementação de todos os métodos definidos na interface ClienteService.
	//Facade: Abstraindo todas as integrações com subsistemas de forma a promover uma interface simples.
	
	@Override
	public Iterable<Cliente> buscarTodos(){		
		return clienteRepository.findAll();		
	}
	
	public Cliente buscarPorId(Long id) {
		Optional<Cliente> cliente = clienteRepository.findById(id);
		return cliente.get();		
	}
	
	// este método irá inserir um novo cliente, de acordo com seu endereço(cep).
	// para isso irá buscar se o seu endereço já existe ou não e irá inseri-lo de acordo com sua condição através do método salvarClienteComCep().	
	@Override
	public void inserir(Cliente cliente) {				
		salvarClienteComCep(cliente);
	}	
	
	
	//este método irá atualizar o cadastro do cliente, de acordo com seu endereco(cep).
	@Override
	public void atualizar(Long id, Cliente cliente) {
		// busca o cliente pelo ID.
		Optional<Cliente> clienteBd = clienteRepository.findById(id);
		if(clienteBd.isPresent()) {
			// verifica se o endereco já existe (de acordo com o CEP).			
			// caso não exista, iremos integrar com o ViaCep e persistir o retorno como o endereco (novo ou já existente).			
			// altera o cliente, vinculando o endereco (novo ou já existente)
			salvarClienteComCep(cliente);			
		}		
	}
	
	// este método irá deletar o cadastro do cliente, de acordo com seu ID.
	@Override
	public void deletar(Long id){
		clienteRepository.deleteById(id);
	}	
	
	// este método irá salvar um cliente de acordo com seu endereco(cep novo ou já existente).
	private void salvarClienteComCep(Cliente cliente) {
		String cep = cliente.getEndereco().getCep();
		Endereco endereco = enderecoRepository.findById(cep).orElseGet(() -> {
			// caso não exista, iremos integrar com o ViaCep e persistir o retorno como novo endereco.
			Endereco novoEndereco = viaCepService.consultarCep(cep);
			enderecoRepository.save(novoEndereco);
			return novoEndereco;
		});
		cliente.setEndereco(endereco);
		// após definido, salvamos o cliente e com o endereco.
		clienteRepository.save(cliente);
	}	
}
