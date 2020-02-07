package com.example.acto_poc.ui.user


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.acto_poc.MainApp
import com.example.acto_poc.R
import com.example.acto_poc.data.network.User
import kotlinx.android.synthetic.main.user_fragment.*
import javax.inject.Inject

class UserFragment : Fragment(),UserAdapter.UserAdapterListener {

    companion object {
        fun newInstance() = UserFragment()
    }

    private lateinit var userAdapter: UserAdapter
    private lateinit var navController: NavController
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (context?.applicationContext as MainApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.user_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = viewModelFactory.create(UserViewModel::class.java)
        userAdapter = UserAdapter(listOf())
        userAdapter.setUserAdapterListener(this)
        user_recycler_view.adapter = userAdapter

        viewModel.loadUsers()
        viewModel.getUserData().observe(viewLifecycleOwner, Observer { t: List<User> ->
            userAdapter.updateData(t)
        })
    }

    override fun OnItemClick(user: User) {
        Toast.makeText(context,user.name,Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
        bundle.putParcelable("user",user)
        navController.navigate(R.id.action_userFragment_to_albumFragment,bundle)
    }

}
