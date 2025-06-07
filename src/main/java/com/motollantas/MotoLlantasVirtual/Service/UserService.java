package com.motollantas.MotoLlantasVirtual.Service ;

    import com.motollantas.MotoLlantasVirtual.domain.User ;
    import java.util.Optional ;

    /**
     *
     * @author esteb
     */
    public interface UserService {

        public Optional<User> findByIdentification(String identification);

        User getCurrentUser();

        User findByEmail(String email);

        void save(User user);

        void deleteByEmail(String email);
    }
