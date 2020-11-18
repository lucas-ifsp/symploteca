package br.edu.ifsp.domain.entities.user;

public class IllegalNumberOfCheckedOutItensException extends RuntimeException{
    public IllegalNumberOfCheckedOutItensException(String message) {
        super(message);
    }
}
