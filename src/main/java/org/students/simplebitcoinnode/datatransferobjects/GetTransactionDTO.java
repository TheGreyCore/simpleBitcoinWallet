package org.students.simplebitcoinnode.datatransferobjects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionDTO {
    private String transactionHash;
    private List<TransactionOutputDTO> inputs;
    private List<TransactionOutputDTO> outputs;
    private String senderPublicKey;
    private LocalDateTime timestamp;
}
