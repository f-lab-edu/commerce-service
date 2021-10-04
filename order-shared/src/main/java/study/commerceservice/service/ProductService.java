package study.commerceservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import study.commerceservice.repository.ProductRepository;

import javax.persistence.EntityManager;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final EntityManager em;
    private final ProductRepository productRepository;
}
