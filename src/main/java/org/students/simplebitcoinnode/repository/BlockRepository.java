package org.students.simplebitcoinnode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.students.simplebitcoinnode.entity.Block;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends JpaRepository<Block, Integer> {
    /**
     * Attempts to find a block header by specified hash
     * @param hash specifies the hash to use for block query
     * @return Optional wrapper object containing BlockHeader if query returned a BlockHeader, otherwise the wrapper contains null value
     */
    Optional<Block> findBlockHeaderByHash(String hash);

    /**
     * Attempts to find a next block that is chained right next to the given block
     * @param previousHash specifies the hash of the previous block to use for querying
     * @return Optional wrapper object containing BlockHeader if query returned a BlockHeader, otherwise the wrapper contains null value
     */
    Optional<Block> findBlockHeaderByPreviousHash(String previousHash);

    /**
     * Attempts to find a block which has specified merkle tree root
     * @param merkleTreeRoot specifies the merkle tree root used for querying
     * @return Optional wrapper object containing BlockHeader instance if query returned objects, otherwise the wrapper contains null value
     */
    Optional<Block> findBlockHeaderByMerkleTreeRoot(String merkleTreeRoot);

    /**
     * Queries all recently mined blocks with specified limit
     * @param limit specifies the amount of blocks to query for
     * @return a list containing BlockHeaders which have recently been mined
     */
    @Query(value = "SELECT * FROM blocks ORDER BY mined_timestamp DESC limit ?1", nativeQuery = true)
    List<Block> findAllByOrderByMinedTimestampLimit(Integer limit);

    /**
     * Recursively traverses transactions Merkle tree and returns a BlockHeader instance whose specified Merkle tree root is the traversed tree's root
     * @param merkleTreeNodeHash specifies the hash of the merkle tree node to use as an entry point for traversal
     * @return Optional wrapper object containing BlockHeader instance if query returned objects, otherwise the wrapper contains a null value
     */
    @Query(value = """
            with recursive search_parent (parent_id, id, hash) as (
                select i.parent_id, i.id, i.hash
                from INTERMEDIATE_MERKLE_TREE_NODES i
                where i.hash = ?1
                union all
                select i.parent_id, i.id, i.hash
                from INTERMEDIATE_MERKLE_TREE_NODES i
                join search_parent s on s.parent_id = i.ID
            )
            select * from BLOCKS
            where MERKLE_TREE_ROOT = (select hash from search_parent where parent_id is null)
            """, nativeQuery = true)
    Optional<Block> findBlockHeaderFromMerkleTreeNode(String merkleTreeNodeHash);
}