package net.adsService.repository;

import net.adsService.model.Message;
import net.adsService.model.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Integer> {

    int countByToIdAndStatus(int toId, MessageStatus status);

    int countByFromIdAndToIdAndStatus(int fromId,int toId,MessageStatus status);

    @Query("select m from Message m where (m.from.id=:id1 and m.to.id=:id2) or (m.from.id=:id2 and m.to.id=:id1)")
    List<Message> findAllMessages(@Param("id1")int id1,@Param("id2")int id2);
}
