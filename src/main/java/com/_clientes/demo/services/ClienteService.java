package com._clientes.demo.services;

import com._clientes.demo.dto.ClienteDTO;
import com._clientes.demo.entities.Cliente;
import com._clientes.demo.repositories.ClienteRepository;
import com._clientes.demo.services.exceptions.DatabaseException;
import com._clientes.demo.services.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository repository;

    @Transactional(readOnly = true)
    public ClienteDTO findById(Long id) {
            Cliente cliente = repository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Recurso não encontrado"));
            return new ClienteDTO(cliente);
    }

    @Transactional(readOnly = true)
    public Page<ClienteDTO> findAll(Pageable pageable) {
        Page<Cliente> result = repository.findAll(pageable);
        return result.map(x -> new ClienteDTO(x));
    }

    @Transactional
    public ClienteDTO insert(ClienteDTO dto) {
        Cliente entity = new Cliente();
        copyDtoToEntity(dto, entity);
        entity = repository.save(entity);
        return new ClienteDTO(entity);
    }

    @Transactional
    public ClienteDTO update(Long id, ClienteDTO dto) {
        try {
            Cliente entity = repository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = repository.save(entity);
            return new ClienteDTO(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso nao encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void copyDtoToEntity(ClienteDTO dto, Cliente entity) {
        entity.setName(dto.getName());
        entity.setIncome(dto.getIncome());
        entity.setBirthDate(dto.getBirthDate());
        entity.setChildren(dto.getChildren());
    }

}
