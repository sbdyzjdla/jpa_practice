import domain.Member;
import domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();
            //logic(em);
            testSave(em);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }

//    public static void logic(EntityManager em) {
//
//        String id = "id1";
//        Member member = new Member();
//        member.setId(id);
//        member.setUsername("지한");
//        member.setAge(2);
//        //등록
//        em.persist(member);
//
//        //수정
//        member.setAge(20);
//
//        //한 건 조회
//        Member findMember = em.find(Member.class, id);
//        System.out.println("find domain.Member Id : " + findMember.getId()
//        + ", member name " + findMember.getUsername()
//        + ", member age : " + findMember.getAge());
//
//        //목록 조회
//        List<Member> members = em.createQuery("select m from domain.Member m", Member.class)
//                .getResultList();
//        System.out.println("Members = " + members);
//        System.out.println("domain.Member.size = " + members.size());
//
//        em.remove(member);
//    }

    //연관관계 매핑(저장)
    public static void testSave(EntityManager em) {

        //팀1 저장
        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        //회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1);
        em.persist(member1);

        //회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1);
        em.persist(member2);

    }

}
