package com.openclassrooms.realestatemanager;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import androidx.room.Room;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.openclassrooms.realestatemanager.models.Estate;
import com.openclassrooms.realestatemanager.models.User;
import com.openclassrooms.realestatemanager.database.database.RoomDb;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
//import static junit.framework.TestCase.assertTrue;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class EstateDaoTest {

    // FOR DATA
    private RoomDb roomDb;

    // DATA SET FOR TEST
    private static int AGENT_ID =1;
    private static User USER_DEMO = new User(AGENT_ID, "seb", "doubletsebastien@sfr.fr");
    private static Estate NEW_ESTATE = new Estate( "Manoir", 250000, 125, 8, 5, 1, "super",  "19 rue dun champs", 39500, "Ounans", false, "now", "non", 1, 0.25455, 5.421);

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void initDb() throws Exception{
        this.roomDb = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getTargetContext(), RoomDb.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void closeDb() throws Exception{
        roomDb.close();
    }

    @Test
    public void insertAndGetUser() throws InterruptedException{
        //BEFORE ADDING USER
        this.roomDb.userDao().createUser(USER_DEMO);
        //TEST
        User user = LiveDataTestUtil.getValue(this.roomDb.userDao().getUser(AGENT_ID));
        assertTrue(user.getName().equals(USER_DEMO.getName())&& user.getAgentId() == AGENT_ID);
    }

    @Test
    public void getEstateWhenNoEstateInserted() throws InterruptedException{
        List<Estate> estates = LiveDataTestUtil.getValue(this.roomDb.estateDao().getEstatePerAgent(AGENT_ID));
        assertTrue(estates.isEmpty());
    }

    @Test
    public void insertAndGetEstates() throws Exception{
        this.roomDb.userDao().createUser(USER_DEMO);
        this.roomDb.estateDao().insertEstate(NEW_ESTATE);

        List<Estate> estates = LiveDataTestUtil.getValue(this.roomDb.estateDao().getEstatePerAgent(AGENT_ID));
        assertEquals(1, estates.size());
    }

    @Test
    public void insertAndUpdateEstate()throws InterruptedException{
        this.roomDb.userDao().createUser(USER_DEMO);
        this.roomDb.estateDao().insertEstate(NEW_ESTATE);
        Estate estate = LiveDataTestUtil.getValue(this.roomDb.estateDao().getEstatePerAgent(AGENT_ID)).get(0);
        estate.setNbRoom(15);
        this.roomDb.estateDao().updateEstate(estate);

        List<Estate> estates = LiveDataTestUtil.getValue(this.roomDb.estateDao().getEstatePerAgent(AGENT_ID));
        assertEquals(1, estates.size());
        assertEquals(15, estates.get(0).getNbRoom());

    }

//    @Test
//    public void insertandDeleteEstate() throws InterruptedException{
//        this.roomDb.userDao().createUser(USER_DEMO);
//        this.roomDb.estateDao().insertEstate(NEW_ESTATE);
//        Estate estate = LiveDataTestUtil.getValue(this.roomDb.estateDao().getEstatePerAgent(AGENT_ID)).get(0);
//        this.roomDb.estateDao().deleteEstate(estate.getEstateId());
//
//        List<Estate> estates = LiveDataTestUtil.getValue(this.roomDb.estateDao().getEstatePerAgent(AGENT_ID));
//        assertEquals(1, estates.size());
//    }

}
