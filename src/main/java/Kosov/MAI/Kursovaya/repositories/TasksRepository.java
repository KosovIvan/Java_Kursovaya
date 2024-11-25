package Kosov.MAI.Kursovaya.repositories;

import Kosov.MAI.Kursovaya.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
}
