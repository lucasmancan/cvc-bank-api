package br.com.cvcbank.services;

import br.com.cvcbank.entities.Transfer;

import java.math.BigDecimal;


/**
 * Calculates fee based in challenge specification
 *
 * 1) O usuário deve poder agendar uma transferência financeira com as seguintes informações:
 * Conta de origem (padrão XXXXXX)
 * Conta de desƟno (padrão XXXXXX)
 * Valor da transferência
 * Taxa (a ser calculada)
 * Data da transferência (data que será realizada a operação)
 * Data de agendamento (hoje)
 *
 * 2) Cada Ɵpo de transação segue uma regra diferente para cálculo da taxa.
 * Obs: As taxas abaixo seguem da mais prioritária para a menos.
 *
 * A: Transferências no mesmo dia do agendamento tem uma taxa de $3 mais 3% do valor a ser
 * transferido;
 *
 * B: Transferências até 10 dias da data de agendamento possuem uma taxa de $12 mulƟplicado
 * pela quanƟdade de dias da data de agendamento até a data de transferência.
 * Ex:
 * Data de agendamento = 10/10/2019
 * Data de transferência = 19/10/2019
 * Valor a ser mulƟplicado = 9
 *
 * © Todos os direitos reservados 4
 *
 * C: Operações do Ɵpo C tem uma taxa regressiva conforme a data de transferência:
 * Acima de 10 até 20 dias da data de agendamento 8%
 * Acima de 20 até 30 dias da data de agendamento 6%
 * Acima de 30 até 40 dias da data de agendamento 4%
 * Acima de 40 dias da data de agendamento e valor superior a 100.000 2%
 */
public interface FeeService {
    BigDecimal calculateByTransfer(Transfer transfer);
}
