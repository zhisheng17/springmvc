package cn.zhisheng.repository;

import cn.zhisheng.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by 10412 on 2016/12/21.
 */
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>
{

}
