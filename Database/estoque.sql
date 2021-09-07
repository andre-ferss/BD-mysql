SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Banco de dados: `jdbc`
--
-- --------------------------------------------------------
--
-- Estrutura da tabela `estoque`
--

CREATE TABLE `estoque` (
  `codigo` int(10) NOT NULL,
  `produto` varchar(30) NOT NULL,
  `quantidade` int(30) NOT NULL,
  `preco` double NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Extraindo dados da tabela `estoque`
--

INSERT INTO `estoque` (`codigo`, `produto`, `quantidade`, `preco`) VALUES
(1, 'Arroz', 30, 7),
(2, 'Feij?o', 30, 10),
(3, 'Frango-Caipira', 30, 12),
(4, 'Lingui?a-Pimenta', 30, 18),
(5, 'Bisteca Su?na', 30, 22),
(6, 'Alm?ndega', 30, 22),
(7, 'Carne Mo?da', 30, 8),
(8, 'Peru', 30, 14),
(9, 'Carne de Pato', 30, 27),
(10, 'Carne de Coelho', 30, 16);

--
-- Índices para tabelas despejadas
--

--
-- Índices para tabela `estoque`
--
ALTER TABLE `estoque`
  ADD PRIMARY KEY (`codigo`);

--
-- AUTO_INCREMENT de tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `estoque`
--
ALTER TABLE `estoque`
  MODIFY `codigo` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;